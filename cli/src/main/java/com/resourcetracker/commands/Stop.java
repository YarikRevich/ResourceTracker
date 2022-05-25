package com.resourcetracker.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import com.resourcetracker.StateService;
import com.resourcetracker.entity.StateEntity.Mode;

import com.resourcetracker.TerraformService;

import com.resourcetracker.ConfigService;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Component
@Command(name = "stop")
public class Stop implements Runnable{
	private static final Logger logger = LogManager.getLogger(Start.class);

	@Option(names = {"-p", "--project"}, description = "project name to start")
	private String project;

	@Autowired
	ConfigService configService;

	@Autowired
	StateService stateService;

	public void run() {
		if (stateService.isMode(Mode.STARTED)){
			TerraformService terraformService = new TerraformService(configService.getParsedConfigFile().cloud.provider);
			terraformService.stop();
		}else{
			logger.info("ResourceTracker is already stoped!");
		}
	}
}
