package com.resourcetracker.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import com.resourcetracker.StateService;
import com.resourcetracker.entity.StateEntity.Mode;

import com.resourcetracker.ConfigService;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
@Command(name = "stop")
public class Stop implements Runnable{
	@Option(names = {"-p", "--project"}, description = "project name to start")
	private String project;

	@Autowired
	ConfigService configService;

	@Autowired
	StateService stateService;

	public void run() {
		if (stateService.isMode(Mode.STARTED)){
			TerraformService terraformService = new TerraformService(configService.cloud.provider);
			terraformService.stop();
		}else{
			logger.info("ResourceTracker is already stoped!");
		}
	}
}
