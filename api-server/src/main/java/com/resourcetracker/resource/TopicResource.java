package com.resourcetracker.resource;

import com.resourcetracker.api.TopicResourceApi;
import com.resourcetracker.model.TopicLogsResult;
import com.resourcetracker.model.TopicLogsUnit;
import com.resourcetracker.service.kafka.KafkaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
        TopicLogsResult topicLogs = new TopicLogsResult();

        topicLogs.setResult(kafkaService
                .consumeLogs()
                .stream()
                .map(element -> {
                    TopicLogsUnit topicLogsUnit = new TopicLogsUnit();

                    topicLogsUnit.setId(element.getId());
                    topicLogsUnit.setData(element.getData());
                    topicLogsUnit.setError(element.getError());
                    topicLogsUnit.setHostname(element.getHostName());
                    topicLogsUnit.setHostaddress(element.getHostAddress());
                    topicLogsUnit.setTimestamp(element.getTimestamp().toString());

                    return topicLogsUnit;
                })
                .toList());

        return topicLogs;
    }
}
