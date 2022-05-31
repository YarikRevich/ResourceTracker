package com.resourcetracker.command;


import com.resourcetracker.TerraformService;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.entity.StateEntity;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import com.resourcetracker.StateService;
import com.resourcetracker.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

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

	@Command
	void start(
			@Option(names = { "-p", "--project" }, description = "project name to start", required = true) String project,
			@Option(names = { "-r", "--request" }, description = "request to start if mode is direct", required = true) String request) {
		configService.parse();

		 if (stateService.isMode(StateEntity.Mode.STOPED)) {
			 ConfigEntity parsedConfigFile = configService.getParsedConfigFile();
			 terraformService.setProvider(parsedConfigFile.getCloud().getProvider());
			 terraformService.start(parsedConfigFile.toTerraformRequestEntity().toJSON());
			 stateService.setMode(StateEntity.Mode.STARTED);
			 stateService.actualizeConfigFileHash();
		 } else {
		 	logger.info("ResourceTracker is already started!");
		 }
	}

	@Command
	void validate(){
		configService.parse();
		System.out.println("Configuration file is valid!");
	}

	@Command
	void status(@Option(names = { "-p", "--project" }, description = "project name to start") String project){
		if (!stateService.isConfigFileHashActual() && stateService.isMode(StateEntity.Mode.STARTED)){
			System.out.println("It seems, that you have modified configuration file. Please, restart current remote execution");
		}else{

		}
	}

	@Command
	void stop(@Option(names = {"-p", "--project"}, description = "project name to start") String project){
		// if (stateService.isMode(Mode.STARTED)){
		// 	TerraformService terraformService = new TerraformService(configService.getParsedConfigFile().cloud.provider);
		// 	terraformService.stop();
		// }else{
		// 	logger.info("ResourceTracker is already stoped!");
		// }
		configService.parse();
		System.out.println("stoped");
	}

//	public Integer call()  {
//		return 0;
//	}
}
