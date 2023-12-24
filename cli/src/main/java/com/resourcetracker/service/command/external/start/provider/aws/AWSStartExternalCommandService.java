package com.resourcetracker.service.command.external.start.provider.aws;

import com.resourcetracker.converter.CredentialsConverter;
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
import com.resourcetracker.service.command.ICommand;
import com.resourcetracker.service.config.ConfigService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents start external command service for AWS provider. */
@Service
public class AWSStartExternalCommandService implements ICommand {
  private static final Logger logger = LogManager.getLogger(AWSStartExternalCommandService.class);

  @Autowired private ConfigService configService;

  @Autowired private ApplyClientCommandService applyClientCommandService;

  @Autowired private SecretsAcquireClientCommandService secretsAcquireClientCommandService;

  @Autowired private ScriptAcquireClientCommandService scriptAcquireClientCommandService;

  /**
   * @see ICommand
   */
  @Override
  public void process() throws ApiServerException {
    ConfigEntity.Cloud.AWSCredentials credentials =
        CredentialsConverter.convert(
            configService.getConfig().getCloud().getCredentials(),
            ConfigEntity.Cloud.AWSCredentials.class);

    ValidationSecretsApplicationDto validationSecretsApplicationDto =
        ValidationSecretsApplicationDto.of(Provider.AWS, credentials.getFile());

    ValidationSecretsApplicationResult validationSecretsApplicationResult =
        secretsAcquireClientCommandService.process(validationSecretsApplicationDto);

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

      ValidationScriptApplicationResult validationScriptApplicationResult =
          scriptAcquireClientCommandService.process(validationScriptApplicationDto);

      if (validationScriptApplicationResult.getValid()) {
        CredentialsFields credentialsFields =
            CredentialsFields.of(
                AWSSecrets.of(
                    validationSecretsApplicationResult.getSecrets().getAccessKey(),
                    validationSecretsApplicationResult.getSecrets().getSecretKey()),
                credentials.getRegion());

        TerraformDeploymentApplication terraformDeploymentApplication =
            TerraformDeploymentApplication.of(requests, Provider.AWS, credentialsFields);

        TerraformDeploymentApplicationResult terraformDeploymentApplicationResult =
            applyClientCommandService.process(terraformDeploymentApplication);

        System.out.printf(
            "Deployment with the given configuration was started, %s!\n",
            terraformDeploymentApplicationResult.getMachineAddress());
      } else {
        logger.fatal(new ScriptDataValidationException().getMessage());
      }
    } else {
      logger.fatal(new CloudCredentialsValidationException().getMessage());
    }
  }
}
