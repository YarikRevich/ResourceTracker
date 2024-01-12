package com.resourcetracker.resource;

import com.resourcetracker.api.TopicResourceApi;
import com.resourcetracker.dto.KafkaLogsTopicDto;
import com.resourcetracker.entity.InternalConfigEntity;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.KafkaServiceNotAvailableException;
import com.resourcetracker.exception.WorkspaceUnitDirectoryNotFoundException;
import com.resourcetracker.exception.WorkspaceUnitInternalConfigFileNotFoundException;
import com.resourcetracker.model.TopicLogsApplication;
import com.resourcetracker.model.TopicLogsResult;
import com.resourcetracker.model.TopicLogsUnit;
import com.resourcetracker.service.kafka.KafkaService;
import com.resourcetracker.service.terraform.workspace.WorkspaceService;
import com.resourcetracker.service.terraform.workspace.facade.WorkspaceFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import lombok.SneakyThrows;

/** Contains implementation of TopicResource. */
@ApplicationScoped
public class TopicResource implements TopicResourceApi {
  @Inject PropertiesEntity properties;

  @Inject WorkspaceFacade workspaceFacade;

  @Inject WorkspaceService workspaceService;

  /**
   * Implementation for declared in OpenAPI configuration v1TopicLogsGet method.
   *
   * @return related Kafka topic messages.
   */
  @Override
  @SneakyThrows
  public TopicLogsResult v1TopicLogsPost(TopicLogsApplication topicLogsApplication) {
    String workspaceUnitKey =
        workspaceFacade.createUnitKey(
            topicLogsApplication.getProvider(), topicLogsApplication.getCredentials());

    if (!workspaceService.isUnitDirectoryExist(workspaceUnitKey)) {
      throw new WorkspaceUnitDirectoryNotFoundException();
    }

    String workspaceUnitDirectory = workspaceService.getUnitDirectory(workspaceUnitKey);

    if (!workspaceService.isInternalConfigFileExist(workspaceUnitDirectory)) {
      throw new WorkspaceUnitInternalConfigFileNotFoundException();
    }

    InternalConfigEntity internalConfig =
        workspaceService.getInternalConfigFileContent(workspaceUnitDirectory);

    KafkaService kafkaService = new KafkaService(internalConfig.getKafka().getHost(), properties);

    if (!kafkaService.isConnected()) {
      throw new KafkaServiceNotAvailableException();
    }

    List<KafkaLogsTopicDto> logs = kafkaService.consumeLogs();

    return TopicLogsResult.of(
        logs.stream()
            .map(
                element ->
                    TopicLogsUnit.of(
                        element.getId(),
                        element.getName(),
                        element.getData(),
                        element.getError(),
                        element.getHostName(),
                        element.getHostAddress(),
                        element.getTimestamp().toString()))
            .toList());
  }
}
