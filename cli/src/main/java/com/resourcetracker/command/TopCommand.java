package com.resourcetracker.command;

import com.resourcetracker.ConfigService;
import com.resourcetracker.StateService;
import com.resourcetracker.TerraformService;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.entity.StateEntity;
import com.resourcetracker.service.KafkaConsumerWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.List;

@Component
@Command(name = "base", mixinStandardHelpOptions = true, description = "Cloud-based remote resource tracker", version = "1.0")
public class TopCommand{
	private static final Logger logger = LogManager.getLogger(TopCommand.class);

	@Autowired
	ConfigService configService;

	@Autowired
	StateService stateService;

	@Autowired
	TerraformService terraformService;

	@Autowired
	KafkaConsumerWrapper kafkaConsumerWrapper;

	@Command
	void start(@Option(names = {"-p", "--project"}, description = "project name to start") String project) {
		 if (stateService.isMode(StateEntity.Mode.STOPED)) {
			 configService.parse();
			 List<ConfigEntity> parsedConfigFile = configService.getParsedConfigFile();
			 for (ConfigEntity configEntity : parsedConfigFile){
				 if (configEntity.getProject().getName() == project || project == null){
					 terraformService.setConfigEntity(configEntity);
					 terraformService.selectProvider();
					 terraformService.start();
					 stateService.setMode(StateEntity.Mode.STARTED);
					 stateService.actualizeConfigFileHash();
					 logger.info(String.format("Project %s is successfully started!", project));
					 break;
				 }
			 }
		 } else {
			 if (project == null){
				 logger.info("Project is already started!");
			 }
		 }
	}

	@Command
	void validate(){
		configService.parse();
		System.out.println("Configuration file is valid!");
	}

	@Command
	void status(@Option(names = { "-p", "--project" }, description = "project name to start") String project) {
		if (!stateService.isConfigFileHashActual() && stateService.isMode(StateEntity.Mode.STARTED)){
			System.out.println("It seems, that you have modified configuration file. Please, restart current remote execution");
		}else if (stateService.isMode(StateEntity.Mode.STARTED)){
			System.out.println(kafkaConsumerWrapper.receiveStatus());
		} else {
			System.out.println("No projects are run");
		}
	}

	@Command
	void stop(@Option(names = {"-p", "--project"}, description = "project name to start") String project){
		 if (stateService.isMode(StateEntity.Mode.STARTED)){
			 configService.parse();
			 for (ConfigEntity configEntity : configService.getParsedConfigFile()){
				 if (configEntity.getProject().getName() == project || project.isEmpty()){
					 terraformService.setConfigEntity(configEntity);
					 terraformService.selectProvider();
					 terraformService.stop();
					 stateService.setMode(StateEntity.Mode.STOPED);
					 stateService.actualizeConfigFileHash();
					 break;
				 }
			 }
		 }else{
			  	logger.info("ResourceTracker is already stoped!");
		 }

		System.out.println("stoped");
	}
}
