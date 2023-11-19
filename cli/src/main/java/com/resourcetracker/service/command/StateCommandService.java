package com.resourcetracker.service.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.TopicResourceApi;
import com.resourcetracker.exception.BodyIsNull;
import com.resourcetracker.model.TopicLogs;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class StateCommandService {
    private static final Logger logger = LogManager.getLogger(StateCommandService.class);

    private final TopicResourceApi topicResourceApi;

    private final ConfigService configService;

    public StateCommandService(@Autowired ConfigService configService) {
        this.configService = configService;

        ApiClient apiClient = new ApiClient()
                .setBasePath(configService.getConfig().getApiServer().getHost());

        this.topicResourceApi = new TopicResourceApi(apiClient);

    }

    public void process() {
        Mono<TopicLogs> response = topicResourceApi.v1TopicLogsGet()
                .doOnError(t -> logger.fatal(t.getMessage()));
        TopicLogs body = response.block();
        if (Objects.isNull(body)) {
            logger.fatal(new BodyIsNull().getMessage());
        }

        StringBuilder output = new StringBuilder();

        body.getResult().forEach(element -> {
            output.append(String.format(
                    "ID: %s\nData: %s\nError: %s\nHostname: %s\nHostaddress: %s\nTimestamp: %s\n\n",
                    element.getId(),
                    element.getData(),
                    element.getError(),
                    element.getHostname(),
                    element.getHostaddress(),
                    element.getTimestamp()
            ));
        });

        System.out.println(output);
    }
}
