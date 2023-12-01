package com.resourcetracker.resource;

import com.resourcetracker.api.TopicResourceApi;
import com.resourcetracker.entity.KafkaLogsTopicEntity;
import com.resourcetracker.model.TopicLogsResult;
import com.resourcetracker.model.TopicLogsUnit;
import com.resourcetracker.service.kafka.KafkaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.ServerErrorException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains implementation of TopicResource.
 */
@ApplicationScoped
public class TopicResource implements TopicResourceApi {
    @Inject
    KafkaService kafkaService;

    /**
     * Implementation for declared in OpenAPI configuration v1TopicLogsGet method.
     * @return related Kafka topic messages.
     */
    @Override
    public TopicLogsResult v1TopicLogsGet() {
        if (!kafkaService.isConnected()){
            throw new InternalServerErrorException();
        }

        List<KafkaLogsTopicEntity> logs = kafkaService.consumeLogs();

        return TopicLogsResult.of(
                logs
                        .stream()
                        .map(element -> TopicLogsUnit.of(
                                element.getId(),
                                        element.getData(),
                                        element.getError(),
                                        element.getHostName(),
                                        element.getHostAddress(),
                                        element.getTimestamp().toString())
                                ).toList()
        );
    }
}
