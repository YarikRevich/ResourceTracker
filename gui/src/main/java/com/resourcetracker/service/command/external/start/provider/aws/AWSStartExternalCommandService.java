package com.resourcetracker.service.command.external.start.provider.aws;

import com.resourcetracker.converter.CredentialsConverter;
import com.resourcetracker.dto.StartExternalCommandResultDto;
import com.resourcetracker.dto.ValidationScriptApplicationDto;
import com.resourcetracker.dto.ValidationSecretsApplicationDto;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.CloudCredentialsValidationException;
import com.resourcetracker.exception.ScriptDataValidationException;
import com.resourcetracker.model.*;
import com.resourcetracker.service.client.command.ApplyClientCommandService;
import com.resourcetracker.service.client.command.ScriptAcquireClientCommandService;
import com.resourcetracker.service.client.command.SecretsAcquireClientCommandService;
import com.resourcetracker.service.command.common.ICommand;
import com.resourcetracker.service.config.ConfigService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents start external command service for AWS provider. */
@Service
public class AWSStartExternalCommandService implements ICommand<StartExternalCommandResultDto> {
  @Autowired private ConfigService configService;

  @Autowired private ApplyClientCommandService applyClientCommandService;

  @Autowired private SecretsAcquireClientCommandService secretsAcquireClientCommandService;

  @Autowired private ScriptAcquireClientCommandService scriptAcquireClientCommandService;

  /**
   * @see ICommand
   */
  @Override
  public StartExternalCommandResultDto process() {
    ConfigEntity.Cloud.AWSCredentials credentials =
        CredentialsConverter.convert(
            configService.getConfig().getCloud().getCredentials(),
            ConfigEntity.Cloud.AWSCredentials.class);

    ValidationSecretsApplicationDto validationSecretsApplicationDto =
        ValidationSecretsApplicationDto.of(Provider.AWS, credentials.getFile());

    ValidationSecretsApplicationResult validationSecretsApplicationResult;
    try {
      validationSecretsApplicationResult =
          secretsAcquireClientCommandService.process(validationSecretsApplicationDto);
    } catch (ApiServerException e) {
      return StartExternalCommandResultDto.of(false, e.getMessage());
    }

    if (validationSecretsApplicationResult.getValid()) {
      List<DeploymentRequest> requests =
          configService.getConfig().getRequests().stream()
              .map(
                  element -> {
                    try {
                      return DeploymentRequest.of(
                          element.getName(),
                          Files.readString(Paths.get(element.getFile())),
                          element.getFrequency());
                    } catch (IOException e) {
                      throw new RuntimeException(e);
                    }
                  })
              .toList();

      ValidationScriptApplicationDto validationScriptApplicationDto =
          ValidationScriptApplicationDto.of(
              requests.stream().map(DeploymentRequest::getScript).toList());

      ValidationScriptApplicationResult validationScriptApplicationResult;
      try {
        validationScriptApplicationResult =
            scriptAcquireClientCommandService.process(validationScriptApplicationDto);
      } catch (ApiServerException e) {
        return StartExternalCommandResultDto.of(false, e.getMessage());
      }

      if (validationScriptApplicationResult.getValid()) {
        CredentialsFields credentialsFields =
            CredentialsFields.of(
                AWSSecrets.of(
                    validationSecretsApplicationResult.getSecrets().getAccessKey(),
                    validationSecretsApplicationResult.getSecrets().getSecretKey()),
                credentials.getRegion());

        TerraformDeploymentApplication terraformDeploymentApplication =
            TerraformDeploymentApplication.of(requests, Provider.AWS, credentialsFields);

        try {
          applyClientCommandService.process(terraformDeploymentApplication);
        } catch (ApiServerException e) {
          return StartExternalCommandResultDto.of(false, e.getMessage());
        }

        return StartExternalCommandResultDto.of(true, null);
      } else {
        return StartExternalCommandResultDto.of(
            false, new ScriptDataValidationException().getMessage());
      }
    } else {
      return StartExternalCommandResultDto.of(
          false, new CloudCredentialsValidationException().getMessage());
    }
  }
}
