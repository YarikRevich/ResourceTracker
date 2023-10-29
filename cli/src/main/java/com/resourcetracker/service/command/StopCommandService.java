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
public class StopCommandService {
  @Autowired
  private ConfigService configService;

  @Autowired
  private APIServerClient apiServerClient;

  public void process() {

//    List<ConfigEntity> parsedConfigFile = configService.getParsedConfigFile();
//    synchronized (this) {
//      for (ConfigEntity configEntity : parsedConfigFile) {
//        new Thread(new StopRunner(configEntity)).run();
//      }
//    }
//    for (ConfigEntity configEntity : parsedConfigFile) {
//      if (configEntity.getProject().getName() == project) {
//        terraformService.setConfigEntity(configEntity);
//        terraformService.stop();
//
//        stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STOPED);
//
//        logger.info(String.format("Project %s is successfully stoped!", project));
//        break;
//      }
//    }
//  } else {
//    logger.info(String.format("Project %s is already stoped!", project));
//  }
  }
//
//	@Override
//	public void run() {
//		if (stateService.isMode(configEntity.getProject().getName(), StateEntity.Mode.STARTED)) {
//			terraformService.setConfigEntity(configEntity);
//			terraformService.stop();
//
//			stateService.removeKafkaBootstrapServer();
//			stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STOPED);
//			numberOfStartedProjects++;
//		}
//	}
}
