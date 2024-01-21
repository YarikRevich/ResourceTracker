package com.resourcetracker.service.command.external.stop.provider.aws;

import com.resourcetracker.converter.CredentialsConverter;
import com.resourcetracker.dto.StopExternalCommandResultDto;
import com.resourcetracker.dto.ValidationSecretsApplicationDto;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.CloudCredentialsValidationException;
import com.resourcetracker.model.*;
import com.resourcetracker.service.client.command.DestroyClientCommandService;
import com.resourcetracker.service.client.command.SecretsAcquireClientCommandService;
import com.resourcetracker.service.command.common.ICommand;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** */
@Service
public class AWSStopExternalCommandService implements ICommand<StopExternalCommandResultDto> {
  @Autowired private ConfigService configService;

  @Autowired private DestroyClientCommandService destroyClientCommandService;

  @Autowired private SecretsAcquireClientCommandService secretsAcquireClientCommandService;

  /**
   * @see ICommand
   */
  @Override
  public StopExternalCommandResultDto process() {
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
      return StopExternalCommandResultDto.of(false, e.getMessage());
    }

    if (validationSecretsApplicationResult.getValid()) {
      CredentialsFields credentialsFields =
          CredentialsFields.of(
              AWSSecrets.of(
                  validationSecretsApplicationResult.getSecrets().getAccessKey(),
                  validationSecretsApplicationResult.getSecrets().getSecretKey()),
              credentials.getRegion());

      TerraformDestructionApplication terraformDestructionApplication =
          TerraformDestructionApplication.of(
              configService.getConfig().getRequests().stream()
                  .map(element -> DestructionRequest.of(element.getName()))
                  .toList(),
              Provider.AWS,
              credentialsFields);

      try {
        destroyClientCommandService.process(terraformDestructionApplication);
      } catch (ApiServerException e) {
        return StopExternalCommandResultDto.of(false, e.getMessage());
      }

      return StopExternalCommandResultDto.of(true, null);
    } else {
      return StopExternalCommandResultDto.of(
          false, new CloudCredentialsValidationException().getMessage());
    }
  }
}
