package com.resourcetracker.service.command.external;

import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.exception.BodyValidationException;
import com.resourcetracker.model.*;
import com.resourcetracker.service.client.command.ApplyClientCommandService;
import com.resourcetracker.service.client.command.CredentialsAcquireClientCommandService;
import com.resourcetracker.service.client.command.SecretsAcquireClientCommandService;
import com.resourcetracker.service.command.ICommand;
import com.resourcetracker.service.config.ConfigService;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/** Represents 'start' command service, which exposes opportunity to execute its processing. */
@Service
public class StartExternalCommandService implements ICommand {
  private static final Logger logger = LogManager.getLogger(StartExternalCommandService.class);

  @Autowired
  private ConfigService configService;

  @Autowired
  private ApplyClientCommandService applyClientCommandService;

  @Autowired
  private CredentialsAcquireClientCommandService credentialsAcquireClientCommandService;

  @Autowired
  private SecretsAcquireClientCommandService secretsAcquireClientCommandService;

  /**
   * @see ICommand
   */
  public void process() throws ApiServerNotAvailableException {
    switch (configService.getConfig().getCloud().getProvider()) {
      case AWS -> validationResourceApi.v1CredentialsAcquirePost(
          Provider.AWS, configService.getConfig().getCloud().getCredentials());
    }

    TerraformDeploymentApplication terraformDeploymentApplication =
        new TerraformDeploymentApplication();

    configService
        .getConfig()
        .getRequests()
        .forEach(
            element -> {
              DeploymentRequest terraformDeploymentRequest = new DeploymentRequest();

              terraformDeploymentRequest.setName(element.getName());
              terraformDeploymentRequest.setFrequency(element.getFrequency());

              String script = configService.getScript(element);
              terraformDeploymentRequest.setScript(script);

              terraformDeploymentApplication.addRequestsItem(terraformDeploymentRequest);
            });

    TerraformDeploymentApplicationCredentials terraformDeploymentRequestCredentials =
        new TerraformDeploymentApplicationCredentials();

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

    Mono<TerraformDeploymentApplicationResult> response =
        terraformResourceApi
            .v1TerraformApplyPost(terraformDeploymentApplication)
            .doOnError(element -> logger.fatal(t.getMessage()))
            .doOnSuccess(element -> logger.info());
    TerraformDeploymentApplicationResult body = response.block();
    if (Objects.isNull(body)) {
      logger.fatal(new BodyValidationException().getMessage());
    }

    System.out.printf(
        "Deployment finished with the given configuration file!\n"
            + "Address of the deployed machine is %s",
        body.getMachineAddress());
  }
}
