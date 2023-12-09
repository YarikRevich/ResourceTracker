package com.resourcetracker.service.resource.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StopCommandService {
  private static final Logger logger = LogManager.getLogger(StopCommandService.class);

  private final TerraformResourceApi terraformResourceApi;

  private final ConfigService configService;

  public StopCommandService(@Autowired ConfigService configService) {
    this.configService = configService;

    ApiClient apiClient =
        new ApiClient().setBasePath(configService.getConfig().getApiServer().getHost());

    this.terraformResourceApi = new TerraformResourceApi(apiClient);
  }

  public void process() {
    TerraformDestructionApplication terraformDestructionApplication =
        new TerraformDestructionApplication();

    configService
        .getConfig()
        .getRequests()
        .forEach(
            element -> {
              terraformDestructionApplication.addNameItem(element.getName());
            });

    Mono<Void> response =
        terraformResourceApi
            .v1TerraformDestroyPost(terraformDestructionApplication)
            .doOnError(t -> logger.fatal(t.getMessage()));
    response.block();

    System.out.println("Deployment with the given configuration file was stopped!");
  }
}
