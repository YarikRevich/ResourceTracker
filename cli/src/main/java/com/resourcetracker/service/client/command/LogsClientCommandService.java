package com.resourcetracker.service.client.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.TopicResourceApi;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.TopicLogsResult;
import com.resourcetracker.service.client.IClientCommand;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;

/** Represents logs topic client command service. */
@Service
public class LogsClientCommandService implements IClientCommand<TopicLogsResult, Void> {
  private final TopicResourceApi topicResourceApi;

  public LogsClientCommandService(@Autowired ConfigService configService) {
    ApiClient apiClient =
        new ApiClient().setBasePath(configService.getConfig().getApiServer().getHost());

    this.topicResourceApi = new TopicResourceApi(apiClient);
  }

  /**
   * @see IClientCommand
   */
  @Override
  public TopicLogsResult process(Void input) throws ApiServerException {
    try {
      return topicResourceApi.v1TopicLogsGet().block();
    } catch (WebClientRequestException e) {
      throw new ApiServerException(new ApiServerNotAvailableException(e.getMessage()));
    }
  }
}
