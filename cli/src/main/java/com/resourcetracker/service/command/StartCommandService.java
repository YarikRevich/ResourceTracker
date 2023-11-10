package com.resourcetracker.service.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Manages starting of each project
 * described in a configuration file
 */
@Service
public class StartCommandService {
  private final TerraformResourceApi terraformResourceApi;

  public StartCommandService(@Autowired ConfigService configService) {
    ApiClient apiClient = new ApiClient()
            .setBasePath(configService.getConfig().getApiServer().getHost());

    terraformResourceApi = new TerraformResourceApi(apiClient);
  }

  public void process() {
    TerraformDeploymentApplication terraformDeploymentApplication = new TerraformDeploymentApplication();
//    terraformDeploymentApplication.addRequestsItem()

    Mono<Void> response = terraformResourceApi.v1TerraformApplyPost(terraformDeploymentApplication)
            .doOnSuccess(t -> System.out.println("Success"))
            .doOnError(t -> System.out.println("Error"));
    response.block();

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
  }

//  @Override
//  public void run() {
//    if (stateService.isMode(configEntity.getProject().getName(), StateEntity.Mode.STOPED)) {
//      terraformService.setConfigEntity(configEntity);
//      terraformService.start();
//
//      stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STARTED);
//      numberOfStartedProjects++;
//    }
//  }
}
