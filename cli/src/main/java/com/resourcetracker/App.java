package com.resourcetracker;

import com.resourcetracker.tools.params.Params;
import com.resourcetracker.tools.params.ParamCallbackDefault;
import com.resourcetracker.config.Config;
import com.resourcetracker.cloud.Manager;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;


import org.springframework.boot.SpringApplication;


@Component
public class App implements CommandLineRunner {
	@Override
	public void run(String... args) {
		System.exit(new CommandLine().execute(args));
	}
}



public class App {
	final static Logger logger = LogManager.getLogger(Loop.class);

	public App(String[] args){
		int exitCode = new CommandLine(new CheckSum()).execute(args);
        System.exit(exitCode);
	}

	public void run(){
		Config.setSrc();

		Params.parse(args);


		// Params.ifValidateDo(new ParamCallbackDefault() {
		// 	public void call() throws Exception {
		// 		if (Config.isValid()) {
		// 			logger.info(Log.VALIDATE_SUCCESS);
		// 		} else {
		// 			logger.info(Log.VALIDATE_FAIL);
		// 		}
		// 	};
		// });

		// Params.ifStartDo(new ParamCallbackDefault() {
		// 	public void call(@Nullable ArrayList<String> args) throws Exception {
		// 		if (Config.isValid()) {
		// 			if (!Manager.isOk()) {
		// 				Manager.start(Config.formatContext());
		// 			} else {
		// 				logger.info("tracker is already started");
		// 			}
		// 		}
		// 	};
		// });

		// Params.ifStopDo(new ParamCallbackDefault() {
		// 	public void call(@Nullable ArrayList<String> args) throws Exception {
		// 		if (Config.isValid()) {
		// 			if (Manager.isOk()) {
		// 				Manager.stop();
		// 			} else {
		// 				logger.info("tracker is already stoped");
		// 			}
		// 		}
		// 	};
		// });

		// Params.ifLogDo(new ParamCallbackDefault() {
		// 	public void call(@Nullable ArrayList<String> args) throws Exception {
		// 		if (Config.isValid()) {
		// 			if (Manager.isOk()) {
		// 				// Manager.stop();
		// 			} else {
		// 				logger.info("tracker is already stoped");
		// 			}
		// 		}
		// 	};
		// });
	}
}
