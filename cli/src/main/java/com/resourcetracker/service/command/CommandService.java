package com.resourcetracker.service.command;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.resourcetracker.ConfigService;
import com.resourcetracker.StateService;
import com.resourcetracker.TerraformService;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.entity.StateEntity;
import com.resourcetracker.service.KafkaConsumerWrapper;

import org.springframework.stereotype.Service;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Service
@Command(name = "base", mixinStandardHelpOptions = true, description = "Cloud-based remote resource tracker", version = "1.0")
public class CommandService {
	private static final Logger logger = LogManager.getLogger(CommandService.class);

	@Autowired
	StateService stateService;

	@Autowired
	TerraformService terraformService;

	@Command
	void start(@Option(names = { "-p", "--project" }, description = "project name to start") String project) {
		List<ConfigEntity> parsedConfigFile = configService.getParsedConfigFile();
		if (project == null) {
			synchronized (this) {
				for (ConfigEntity configEntity : parsedConfigFile) {
					new Thread(new StartRunner(configEntity)).run();
				}
			}
		} else if (stateService.isMode(project, StateEntity.Mode.STOPED)) {
			for (ConfigEntity configEntity : parsedConfigFile) {
				if (configEntity.getProject().getName() == project) {
					terraformService.setConfigEntity(configEntity);
					String kafkaBootstrapServer = terraformService.start();

					stateService.setKafkaBootstrapServer(kafkaBootstrapServer);
					stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STARTED);
					logger.info(String.format("Project %s is successfully started!", project));
					break;
				}
			}
		} else {
			logger.info(String.format("Project %s is already started!", project));
		}
	}

	@Command
	void status(@Option(names = { "-p", "--project" }, description = "project name to start") String project) {
		if (!stateService.isConfigFileHashActual()) {
			System.out.println("**It seems, that you have modified configuration file**");
		}

		List<ConfigEntity> parsedConfigFile = configService.getParsedConfigFile();
		if (project != null) {
			if (stateService.isMode(project, StateEntity.Mode.STARTED)) {
				System.out.println(kafkaConsumerWrapper.receiveStatus(project));
			} else {
				System.out.println(String.format("Project %s is not run!", project));
			}
		} else {
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

	@Command
	void stop(@Option(names = { "-p", "--project" }, description = "project name to start") String project) {
		configService.parse();
		List<ConfigEntity> parsedConfigFile = configService.getParsedConfigFile();
		if (project == null) {
			synchronized (this) {
				for (ConfigEntity configEntity : parsedConfigFile) {
					new Thread(new StopRunner(configEntity)).run();
				}
			}
		} else if (stateService.isMode(project, StateEntity.Mode.STARTED)) {
			for (ConfigEntity configEntity : parsedConfigFile) {
				if (configEntity.getProject().getName() == project) {
					terraformService.setConfigEntity(configEntity);
					terraformService.stop();

					stateService.setMode(configEntity.getProject().getName(), StateEntity.Mode.STOPED);

					logger.info(String.format("Project %s is successfully stoped!", project));
					break;
				}
			}
		} else {
			logger.info(String.format("Project %s is already stoped!", project));
		}
	}

	@Command
	void logs() {
		// TODO: read logs writen to remote Kafka instance
	}
}
