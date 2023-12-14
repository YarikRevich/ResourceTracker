package com.resourcetracker.service.client.command;

import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogsCommandService {
  @Autowired private ConfigService configService;

  public void process() {
    //
    //        if (stateService.isMode(project, StateEntity.Mode.STARTED)) {
    //            System.out.println(kafkaConsumerWrapper.receiveStatus(project));
    //        } else {
    //            System.out.println(String.format("Project %s is not run!", project));
    //        }
    //    } else {
    //        int numberOfRunProjects = 0;
    //        for (ConfigEntity configEntity : parsedConfigFile) {
    //            if (stateService.isMode(configEntity.getProject().getName(),
    // StateEntity.Mode.STARTED)) {
    //
    // System.out.println(kafkaConsumerWrapper.receiveStatus(configEntity.getProject().getName()));
    //                numberOfRunProjects++;
    //            }
    //        }
    //        if (numberOfRunProjects > 0) {
    //            System.out.println("No projects are run!");
    //        }
    //    }
  }
}
