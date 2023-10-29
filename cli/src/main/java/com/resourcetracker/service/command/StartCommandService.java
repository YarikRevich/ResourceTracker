package com.resourcetracker.service.command;

import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.resource.APIServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Manages starting of each project
 * described in a configuration file
 */
@Service
public class StartCommandService {
  @Autowired
  private ConfigService configService;

  @Autowired
  private APIServerClient apiServerClient;

  public void process() {
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
