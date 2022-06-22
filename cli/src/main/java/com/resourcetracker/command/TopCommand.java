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

	private int numberOfStartedProjects = 0;

	/**
	 * Manages starting of each project
	 * described in a configuration file
	 */
	class StartRunner implements Runnable{
		private ConfigEntity configEntity;
		public StartRunner(ConfigEntity configEntity){
			this.configEntity = configEntity;
		}
		/**
		 *
		 */
		@Override
		public void run() {
			if (stateService.isMode(configEntity.getProject().getName(), StateEntity.Mode.STOPED)){
				terraformService.setConfigEntity(configEntity);
				terraformService.start();

				stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STARTED);
				numberOfStartedProjects++;
			}
		}
	}

	@Command
	void start(@Option(names = {"-p", "--project"}, description = "project name to start") String project) {
		configService.parse();
		List<ConfigEntity> parsedConfigFile = configService.getParsedConfigFile();
		if (project == null){
			synchronized (this) {
				for (ConfigEntity configEntity : parsedConfigFile) {
					new Thread(new StartRunner(configEntity)).run();
				}
			}
			stateService.actualizeConfigFileHash();
			if (this.numberOfStartedProjects > 0) {
				logger.info("All projects were successfully started!");
			}
		} else if (stateService.isMode(project, StateEntity.Mode.STOPED)){
			for (ConfigEntity configEntity : parsedConfigFile){
				if (configEntity.getProject().getName() == project || project == null){
					terraformService.setConfigEntity(configEntity);
					terraformService.start();

					stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STARTED);
					stateService.actualizeConfigFileHash();
					logger.info(String.format("Project %s is successfully started!", project));
					break;
				}
			}
		 } else {
				 logger.info(String.format("Project %s is already started!", project));
		 }
	}

	@Command
	void validate(){
		configService.parse();
		System.out.println("Configuration file is valid!");
	}

	@Command
	void status(@Option(names = { "-p", "--project" }, description = "project name to start") String project) {
		if (!stateService.isConfigFileHashActual()){
			System.out.println("**It seems, that you have modified configuration file**");
		}

		configService.parse();
		List<ConfigEntity> parsedConfigFile = configService.getParsedConfigFile();
		if (project != null){
			if (stateService.isMode(project, StateEntity.Mode.STARTED)) {
				System.out.println(kafkaConsumerWrapper.receiveStatus(project));
			}else{
				System.out.println(String.format("Project %s is not run!", project));
			}
		}else {
				int numberOfRunProjects = 0;
				for (ConfigEntity configEntity : parsedConfigFile) {
					if (stateService.isMode(configEntity.getProject().getName(), StateEntity.Mode.STARTED)) {
						System.out.println(kafkaConsumerWrapper.receiveStatus(configEntity.getProject().getName()));
						numberOfRunProjects++;
					}
				}
				if (numberOfRunProjects > 0) {
					System.out.println("No projects are run!");
				}
		}
	}

	/**
	 * Manages starting of each project
	 * described in a configuration file
	 */
	class StopRunner implements Runnable{
		private ConfigEntity configEntity;
		public StopRunner(ConfigEntity configEntity){
			this.configEntity = configEntity;
		}
		/**
		 *
		 */
		@Override
		public void run() {
			if (stateService.isMode(configEntity.getProject().getName(), StateEntity.Mode.STARTED)){
				terraformService.setConfigEntity(configEntity);
				terraformService.stop();

				stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STOPED);
				numberOfStartedProjects++;
			}
		}
	}

	@Command
	void stop(@Option(names = {"-p", "--project"}, description = "project name to start") String project){
		configService.parse();
		List<ConfigEntity> parsedConfigFile = configService.getParsedConfigFile();
		if (project == null){
			synchronized (this) {
				for (ConfigEntity configEntity : parsedConfigFile) {
					new Thread(new StopRunner(configEntity)).run();
				}
			}
			stateService.actualizeConfigFileHash();
			if (numberOfStartedProjects > 0) {
				logger.info("All projects were successfully stoped!");
			}
		} else if (stateService.isMode(project, StateEntity.Mode.STARTED)){
			for (ConfigEntity configEntity : parsedConfigFile){
				if (configEntity.getProject().getName() == project){
					terraformService.setConfigEntity(configEntity);
					terraformService.stop();

					stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STOPED);
					stateService.actualizeConfigFileHash();
					logger.info(String.format("Project %s is successfully stoped!", project));
					break;
				}
			}
		} else {
			logger.info(String.format("Project %s is already stoped!", project));
		}
	}
}
