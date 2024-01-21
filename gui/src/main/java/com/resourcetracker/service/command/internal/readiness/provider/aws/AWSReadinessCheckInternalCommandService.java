package com.resourcetracker.service.command.internal.readiness.provider.aws;

import com.resourcetracker.converter.CredentialsConverter;
import com.resourcetracker.dto.ReadinessCheckInternalCommandResultDto;
import com.resourcetracker.dto.ValidationSecretsApplicationDto;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.exception.CloudCredentialsValidationException;
import com.resourcetracker.model.*;
import com.resourcetracker.service.client.command.ReadinessCheckClientCommandService;
import com.resourcetracker.service.client.command.SecretsAcquireClientCommandService;
import com.resourcetracker.service.command.common.ICommand;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AWSReadinessCheckInternalCommandService
    implements ICommand<ReadinessCheckInternalCommandResultDto> {
  @Autowired private ConfigService configService;

  @Autowired private SecretsAcquireClientCommandService secretsAcquireClientCommandService;

  @Autowired private ReadinessCheckClientCommandService readinessCheckClientCommandService;

  /**
   * @see ICommand
   */
  @Override
  public ReadinessCheckInternalCommandResultDto process() {
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
      return ReadinessCheckInternalCommandResultDto.of(false, e.getMessage());
    }

    if (validationSecretsApplicationResult.getValid()) {
      CredentialsFields credentialsFields =
          CredentialsFields.of(
              AWSSecrets.of(
                  validationSecretsApplicationResult.getSecrets().getAccessKey(),
                  validationSecretsApplicationResult.getSecrets().getSecretKey()),
              credentials.getRegion());

      ReadinessCheckApplication readinessCheckApplication =
          ReadinessCheckApplication.of(Provider.AWS, credentialsFields);

      ReadinessCheckResult readinessCheckResult;
      try {
        readinessCheckResult =
            readinessCheckClientCommandService.process(readinessCheckApplication);
      } catch (ApiServerException e) {
        return ReadinessCheckInternalCommandResultDto.of(false, e.getMessage());
      }

      if (readinessCheckResult.getStatus() == ReadinessCheckStatus.DOWN) {
        return ReadinessCheckInternalCommandResultDto.of(
            false,
            new ApiServerException(
                    new ApiServerNotAvailableException(readinessCheckResult.getData().toString())
                        .getMessage())
                .getMessage());
      }

      return ReadinessCheckInternalCommandResultDto.of(true, null);
    } else {
      return ReadinessCheckInternalCommandResultDto.of(
          false, new CloudCredentialsValidationException().getMessage());
    }
  }
}
