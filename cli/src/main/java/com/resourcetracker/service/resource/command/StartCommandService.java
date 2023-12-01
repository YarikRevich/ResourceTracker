package com.resourcetracker.service.resource.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.api.ValidationResourceApi;
import com.resourcetracker.exception.BodyValidationException;
import com.resourcetracker.model.*;
import com.resourcetracker.model.RequestCredentials;
import com.resourcetracker.model.Request;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Represents 'start' command service, which exposes
 * opportunity to execute its processing.
 */
@Service
public class StartCommandService {
  private static final Logger logger = LogManager.getLogger(StartCommandService.class);

  private final TerraformResourceApi terraformResourceApi;

  private final ValidationResourceApi validationResourceApi;

  private final ConfigService configService;

  public StartCommandService(@Autowired ConfigService configService) {
    this.configService = configService;

    ApiClient apiClient = new ApiClient()
            .setBasePath(configService.getConfig().getApiServer().getHost());

    this.terraformResourceApi = new TerraformResourceApi(apiClient);
    this.validationResourceApi = new ValidationResourceApi(apiClient);
  }

  /**
   * Provides command process execution.
   */
  public void process() {
    switch (configService.getConfig().getCloud().getProvider()) {
      case AWS -> {
        validationResourceApi.v1CredentialsAcquirePost(Provider.AWS, configService.getConfig().getCloud().getCredentials());
      }
    }









    TerraformDeploymentApplication terraformDeploymentApplication = new TerraformDeploymentApplication();

    configService.getConfig().getRequests().forEach(element -> {
      DeploymentRequest terraformDeploymentRequest = new DeploymentRequest();

      terraformDeploymentRequest.setName(element.getName());
      terraformDeploymentRequest.setFrequency(element.getFrequency());

      String script = configService.getScript(element);
      terraformDeploymentRequest.setScript(script);


      terraformDeploymentApplication.addRequestsItem(terraformDeploymentRequest);
    });

    TerraformDeploymentApplicationCredentials terraformDeploymentRequestCredentials = new TerraformDeploymentApplicationCredentials();

    switch (configService.getConfig().getCloud().getProvider()) {
      case AWS -> {

        terraformDeploymentRequest.setProvider(Provider.AWS);

        ConfigEntity.Cloud.AWSCredentials credentials = configService.getCredentials();

        terraformDeploymentRequestCredentials.setCredentialsFile(credentials.getFile());
        terraformDeploymentRequestCredentials.setRegion(credentials.getRegion());
        terraformDeploymentRequestCredentials.setProfile(credentials.getProfile());
      }
    }

    terraformDeploymentRequest.setCredentials(terraformDeploymentRequestCredentials);

    terraformDeploymentApplication.setCredentials(terraformDeploymentRequestCredentials);

    Mono<TerraformDeploymentApplicationResult> response = terraformResourceApi.v1TerraformApplyPost(terraformDeploymentApplication)
            .doOnError(t -> logger.fatal(t.getMessage()))
            .doOnSuccess(t -> logger.info());
    TerraformDeploymentApplicationResult body = response.block();
    if (Objects.isNull(body)) {
      logger.fatal(new BodyValidationException().getMessage());
    }

    System.out.printf("Deployment finished with the given configuration file!\nAddress of the deployed machine is %s", body.getMachineAddress());
  }
}
