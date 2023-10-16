package com.resourcetracker.service.command;

import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Manages starting of each project
 * described in a configuration file
 */
@Service
public class StopCommandRunner {
	@Autowired
	private ConfigService configService;
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
