package com.resourcetracker.command;

import picocli.CommandLine;
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

@Command(name = "base", mixinStandardHelpOptions = true, description = "Cloud-based remote resource tracker", version = "1.0")
public class TopCommand implements Runnable{
	private static final Logger logger = LogManager.getLogger(TopCommand.class);

	@Autowired
	ConfigService configService;

	@Autowired
	StateService stateService;

	// @Component
	@Command
	void start(
			@Option(names = { "-p", "--project" }, description = "project name to start", required = true) String project,
			@Option(names = { "-r", "--request" }, description = "request to start if mode is direct", required = true) String request) {
		System.out.println("start command");
		// if (stateService.isMode(Mode.STOPED)) {
		// TerraformService terraformService = new TerraformService(
		// configService.getParsedConfigFile().cloud.provider);
		// terraformService.start(configService.getParsedConfigFile().toTerraformRequestEntity().toJSON());
		// } else {
		// logger.info("ResourceTracker is already started!");
		// }
	}

	// @Component
	@Command
	void validate(){

		System.out.println("it is validated");
	}

	// @Component
	@Command
	void status(@Option(names = { "-p", "--project" }, description = "project name to start") String project){
		System.out.println("It works");
	}

	// @Component
	@Command
	void stop(@Option(names = {"-p", "--project"}, description = "project name to start") String project){
		// if (stateService.isMode(Mode.STARTED)){
		// 	TerraformService terraformService = new TerraformService(configService.getParsedConfigFile().cloud.provider);
		// 	terraformService.stop();
		// }else{
		// 	logger.info("ResourceTracker is already stoped!");
		// }
		System.out.println("stoped");
	}

	@Override
	public void run(){

	}
}
