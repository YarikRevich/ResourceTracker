package com.resourcetracker.service.client.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.model.TerraformDeploymentApplicationResult;

/** Manages starting of each project */
@Service
public class ApplyClientCommandService implements IClientCommand<TerraformDeploymentApplicationResult> {
  private static final Logger logger = LogManager.getLogger(ApplyClientCommandService.class);

    private final TerraformResourceApi terraformResourceApi;

    public ApplyClientCommandService(@Autowired ConfigService configService) {
      ApiClient apiClient = new ApiClient()
              .setBasePath(configService.getConfig().getApiServer().getHost());

      this.terraformResourceApi = new TerraformResourceApi(apiClient);
    }

  //  public void process() {
  //    TerraformDeploymentApplication terraformDeploymentApplication = new
  // TerraformDeploymentApplication();
  ////    terraformDeploymentApplication.addRequestsItem()
  //
  //    Mono<Void> response =
  // terraformResourceApi.v1TerraformApplyPost(terraformDeploymentApplication)
  //            .doOnError(t -> logger.fatal(new StartCommandFailException().getMessage()));
  //    response.block();

  //    List<ConfigEntity> parsedConfigFile = configService.getParsedConfigFile();
  //
  //    synchronized (this) {
  //      for (ConfigEntity configEntity : parsedConfigFile) {
  //        new Thread(new StartRunner(configEntity)).run();
  //      }
  //    }
  //
  //    for (ConfigEntity configEntity : parsedConfigFile) {
  //      if (configEntity.getProject().getName() == project) {
  //        terraformService.setConfigEntity(configEntity);
  //        String kafkaBootstrapServer = terraformService.start();
  //
  //        stateService.setKafkaBootstrapServer(kafkaBootstrapServer);
  //        stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STARTED);
  //        logger.info(String.format("Project %s is successfully started!", project));
  //        break;
  //      }
  //    }
  //  }

  /**
   * @return
   * @throws ApiServerNotAvailableException
   */
  @Override
  public TerraformDeploymentApplicationResult process() throws ApiServerNotAvailableException {

    return null;
  }
}
