package com.resourcetracker.service.client.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.HealthResourceApi;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.ReadinessCheckApplication;
import com.resourcetracker.model.ReadinessCheckResult;
import com.resourcetracker.service.client.common.IClientCommand;
import com.resourcetracker.service.config.ConfigService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/** Represents readiness check client command service. */
@Service
public class ReadinessCheckClientCommandService
    implements IClientCommand<ReadinessCheckResult, ReadinessCheckApplication> {
  @Autowired private ConfigService configService;

  private HealthResourceApi healthResourceApi;

  /**
   * @see IClientCommand
   */
  @Override
  @PostConstruct
  public void configure() {
    ApiClient apiClient =
        new ApiClient().setBasePath(configService.getConfig().getApiServer().getHost());

    this.healthResourceApi = new HealthResourceApi(apiClient);
  }

  /**
   * @see IClientCommand
   */
  public ReadinessCheckResult process(ReadinessCheckApplication input) throws ApiServerException {
    try {
      return healthResourceApi.v1ReadinessPost(input).block();
    } catch (WebClientResponseException e) {
      throw new ApiServerException(e.getResponseBodyAsString());
    } catch (WebClientRequestException e) {
      throw new ApiServerException(new ApiServerNotAvailableException(e.getMessage()).getMessage());
    }
  }
}
