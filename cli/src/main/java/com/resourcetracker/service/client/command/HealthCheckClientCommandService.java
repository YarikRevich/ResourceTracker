package com.resourcetracker.service.client.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.HealthCheckResourceApi;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.HealthCheckResult;
import com.resourcetracker.service.client.IClientCommand;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;

/** Represents health check client command service. */
@Service
public class HealthCheckClientCommandService implements IClientCommand<HealthCheckResult, Void> {
  private final HealthCheckResourceApi healthCheckResourceApi;

  public HealthCheckClientCommandService(@Autowired ConfigService configService) {
    ApiClient apiClient =
        new ApiClient().setBasePath(configService.getConfig().getApiServer().getHost());

    this.healthCheckResourceApi = new HealthCheckResourceApi(apiClient);
  }

  /**
   * @see IClientCommand
   */
  public HealthCheckResult process(Void input) throws ApiServerException {
    try {
      return healthCheckResourceApi.v1HealthGet().block();
    } catch (WebClientRequestException e) {
      throw new ApiServerException(new ApiServerNotAvailableException(e.getMessage()));
    }
  }
}
