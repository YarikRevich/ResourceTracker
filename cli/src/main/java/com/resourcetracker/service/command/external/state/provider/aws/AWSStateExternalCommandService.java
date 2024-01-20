package com.resourcetracker.service.command.external.state.provider.aws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resourcetracker.converter.CredentialsConverter;
import com.resourcetracker.dto.ValidationSecretsApplicationDto;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.*;
import com.resourcetracker.service.client.command.LogsClientCommandService;
import com.resourcetracker.service.client.command.SecretsAcquireClientCommandService;
import com.resourcetracker.service.command.ICommand;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.visualization.state.VisualizationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/** Represents start external command service for AWS provider. */
@Service
public class AWSStateExternalCommandService implements ICommand {
  @Autowired private ConfigService configService;

  @Autowired private SecretsAcquireClientCommandService secretsAcquireClientCommandService;

  @Autowired private LogsClientCommandService logsClientCommandService;

  @Autowired private VisualizationState visualizationState;

  /**
   * @see ICommand
   */
  @Override
  public void process() throws ApiServerException {
    visualizationState.getLabel().pushNext();

    ConfigEntity.Cloud.AWSCredentials credentials =
        CredentialsConverter.convert(
            configService.getConfig().getCloud().getCredentials(),
            ConfigEntity.Cloud.AWSCredentials.class);

    ValidationSecretsApplicationDto validationSecretsApplicationDto =
        ValidationSecretsApplicationDto.of(Provider.AWS, credentials.getFile());

    ValidationSecretsApplicationResult validationSecretsApplicationResult =
        secretsAcquireClientCommandService.process(validationSecretsApplicationDto);

    if (validationSecretsApplicationResult.getValid()) {
      visualizationState.getLabel().pushNext();

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
      } catch (WebClientResponseException e) {
        throw new ApiServerException(
            new ApiServerNotAvailableException(e.getResponseBodyAsString()).getMessage());
      }

      visualizationState.getLabel().pushNext();

      ObjectMapper mapper = new ObjectMapper();
      topicLogsResult
          .getResult()
          .forEach(
              element -> {
                try {
                  visualizationState.addResult(mapper.writeValueAsString(element));
                } catch (JsonProcessingException e) {
                  throw new RuntimeException(e);
                }
              });
    }
  }
}
