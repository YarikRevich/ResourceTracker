package com.resourcetracker.service.command.external;

import com.resourcetracker.ApiClient;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.DestructionRequest;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.client.command.CredentialsAcquireClientCommandService;
import com.resourcetracker.service.client.command.DestroyClientCommandService;
import com.resourcetracker.service.client.command.SecretsAcquireClientCommandService;
import com.resourcetracker.service.command.ICommand;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StopExternalCommandService implements ICommand {
  private static final Logger logger = LogManager.getLogger(StopExternalCommandService.class);

  @Autowired
  private ConfigService configService;

  @Autowired
  private DestroyClientCommandService destroyClientCommandService;

  @Autowired
  private CredentialsAcquireClientCommandService credentialsAcquireClientCommandService;

  @Autowired
  private SecretsAcquireClientCommandService secretsAcquireClientCommandService;

  /**
   * @see ICommand
   */
  public void process() throws ApiServerNotAvailableException {
    TerraformDestructionApplication terraformDestructionApplication =
        new TerraformDestructionApplication();

    configService
        .getConfig()
        .getRequests()
        .forEach(element -> terraformDestructionApplication.addRequestsItem(
                DestructionRequest.of(element.getName())));

    destroyClientCommandService.process();
    Mono<Void> response =
        terraformResourceApi
            .v1TerraformDestroyPost(terraformDestructionApplication)
            .doOnError(t -> logger.fatal(t.getMessage()));
    response.block();

    System.out.println("Deployment with the given configuration file was stopped!");
  }
}
