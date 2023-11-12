package com.resourcetracker.resource;

import com.resourcetracker.api.TopicResourceApi;
import com.resourcetracker.model.TopicLogs;
import com.resourcetracker.model.TopicLogsResult;
import com.resourcetracker.service.kafka.KafkaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TopicResource implements TopicResourceApi {
    @Inject
    KafkaService kafkaService;

    /**
     * @return
     */
    @Override
    public TopicLogs v1TopicLogsGet() {
        TopicLogs topicLogs = new TopicLogs();

        topicLogs.setResult(kafkaService
                .consumeLogs()
                .stream()
                .map(element -> {
                    TopicLogsResult topicLogsResult = new TopicLogsResult();

                    topicLogsResult.setId(element.getId());
                    topicLogsResult.setData(element.getData());
                    topicLogsResult.setError(element.getError());
                    topicLogsResult.setHostname(element.getHostName());
                    topicLogsResult.setHostaddress(element.getHostAddress());
                    topicLogsResult.setTimestamp(element.getTimestamp().toString());

                    return topicLogsResult;
                })
                .collect(Collectors.toList()));

        return topicLogs;
    }
}
