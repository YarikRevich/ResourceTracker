package com.resourcetracker.service.client.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.HealthCheckResourceApi;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.HealthCheckResult;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;

/** */
@Service
public class HealthClientCommandService implements IClientCommand<HealthCheckResult> {
  private static final Logger logger = LogManager.getLogger(HealthClientCommandService.class);

  private final HealthCheckResourceApi healthCheckResourceApi;

  public HealthClientCommandService(@Autowired ConfigService configService) {
    ApiClient apiClient =
        new ApiClient().setBasePath(configService.getConfig().getApiServer().getHost());

    this.healthCheckResourceApi = new HealthCheckResourceApi(apiClient);
  }

  /**
   * @see IClientCommand
   */
  public HealthCheckResult process() throws ApiServerNotAvailableException {
    try {
      return healthCheckResourceApi.v1HealthGet().block();
    } catch (WebClientRequestException e) {
      throw new ApiServerNotAvailableException(e.getMessage());
    }
  }
}
