package com.resourcetracker.service.command;

import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.StartCommandFailException;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Manages starting of each project
 */
@Service
public class StartCommandService {
  private static final Logger logger = LogManager.getLogger(StartCommandService.class);

  private final TerraformResourceApi terraformResourceApi;

  private final ConfigService configService;

  /**
   *
   * @param configService
   */
  public StartCommandService(@Autowired ConfigService configService) {
    this.configService = configService;

    ApiClient apiClient = new ApiClient()
            .setBasePath(configService.getConfig().getApiServer().getHost());

    this.terraformResourceApi = new TerraformResourceApi(apiClient);

  }

  /**
   *
   */
  public void process() {
    TerraformDeploymentApplication terraformDeploymentApplication = new TerraformDeploymentApplication();

    configService.getConfig().getRequests().forEach(element -> {
      Request terraformDeploymentRequest = new Request();

      terraformDeploymentRequest.setName(element.getName());
      terraformDeploymentRequest.setFrequency(element.getFrequency());

      String script = configService.getScript(element);
      terraformDeploymentRequest.setScript(script);

      RequestCredentials terraformDeploymentRequestCredentials = new RequestCredentials();

      switch (configService.getConfig().getCloud().getProvider()) {
        case AWS -> {
          terraformDeploymentRequest.setProvider(Provider.AWS);

          ConfigEntity.Cloud.AWSCredentials credentials = configService.getCredentials();
          terraformDeploymentRequestCredentials.setAccessKey(credentials.get);
        }
      }


      terraformDeploymentRequest.setCredentials(terraformDeploymentRequestCredentials);

      terraformDeploymentApplication.addRequestsItem(terraformDeploymentRequest);
    });


    Mono<TerraformDeploymentApplicationResult> response = terraformResourceApi.v1TerraformApplyPost(terraformDeploymentApplication)
            .doOnError(t -> logger.fatal(t.getMessage()));
    response.block();

    System.out.println("Deployment finished with the given configuration file!");
  }
}
