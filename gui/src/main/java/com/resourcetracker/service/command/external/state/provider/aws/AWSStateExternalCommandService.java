package com.resourcetracker.service.command.external.state.provider.aws;

import com.resourcetracker.converter.CredentialsConverter;
import com.resourcetracker.dto.StateExternalCommandResultDto;
import com.resourcetracker.dto.ValidationSecretsApplicationDto;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.CloudCredentialsValidationException;
import com.resourcetracker.model.*;
import com.resourcetracker.service.client.command.LogsClientCommandService;
import com.resourcetracker.service.client.command.SecretsAcquireClientCommandService;
import com.resourcetracker.service.command.common.ICommand;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents start external command service for AWS provider. */
@Service
public class AWSStateExternalCommandService implements ICommand<StateExternalCommandResultDto> {
  @Autowired private ConfigService configService;

  @Autowired private SecretsAcquireClientCommandService secretsAcquireClientCommandService;

  @Autowired private LogsClientCommandService logsClientCommandService;

  /**
   * @see ICommand
   */
  @Override
  public StateExternalCommandResultDto process() {
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
      return StateExternalCommandResultDto.of(null, false, e.getMessage());
    }

    if (validationSecretsApplicationResult.getValid()) {
      CredentialsFields credentialsFields =
          CredentialsFields.of(
              AWSSecrets.of(
                  validationSecretsApplicationResult.getSecrets().getAccessKey(),
                  validationSecretsApplicationResult.getSecrets().getSecretKey()),
              credentials.getRegion());

      TopicLogsResult topicLogsResult;

      TopicLogsApplication topicLogsApplication =
          TopicLogsApplication.of(Provider.AWS, credentialsFields);

      try {
        topicLogsResult = logsClientCommandService.process(topicLogsApplication);
      } catch (ApiServerException e) {
        return StateExternalCommandResultDto.of(null, false, e.getMessage());
      }

      return StateExternalCommandResultDto.of(topicLogsResult, true, null);
    } else {
      return StateExternalCommandResultDto.of(
          null, false, new CloudCredentialsValidationException().getMessage());
    }
  }
}
