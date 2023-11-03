package com.resourcetracker.service.command;

import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.resource.APIServerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateCommandService {
    @Autowired
    private ConfigService configService;

    @Autowired
    private APIServerClient apiServerClient;

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
//            if (stateService.isMode(configEntity.getProject().getName(), StateEntity.Mode.STARTED)) {
//                System.out.println(kafkaConsumerWrapper.receiveStatus(configEntity.getProject().getName()));
//                numberOfRunProjects++;
//            }
//        }
//        if (numberOfRunProjects > 0) {
//            System.out.println("No projects are run!");
//        }
//    }
    }
}
