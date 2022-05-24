package com.resourcetracker.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import com.resourcetracker.StateService;
import com.resourcetracker.entity.StateEntity.Mode;

import com.resourcetracker.ConfigService;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Component
@Command(name = "start")
public class Start implements Runnable{
	private static final Logger logger = LogManager.getLogger(Start.class);

	@Option(names = {"-p", "--project"}, description = "project name to start", required = true)
	private String project;

	@Option(names = {"-r", "--request"}, description = "request to start if mode is direct")
	private String request;

	@Autowired
	ConfigService configService;

	@Autowired
	StateService stateService;

	public void run() {
		if (stateService.isMode(Mode.STOPED)){
			TerraformService terraformService = new TerraformService(configService.cloud.provider);
			terraformService.start(configService.toTerraformRequestEntity().toJSON());
		}else{
			logger.info("ResourceTracker is already started!");
		}
	}
}
