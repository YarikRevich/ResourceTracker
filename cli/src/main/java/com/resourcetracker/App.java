package com.resourcetracker;

import picocli.CommandLine;
import com.resourcetracker.commands.Base;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;

@Component
public class App implements CommandLineRunner {
	@Override
	public void run(String... args) {
		System.exit(new CommandLine(new Base()).execute(args));
	}
}


// public class App {
// 	final static Logger logger = LogManager.getLogger(Loop.class);

// 	public App(String[] args){
// 		int exitCode = new CommandLine(new CheckSum()).execute(args);
//         System.exit(exitCode);
// 	}

// 	public void run(){
// 		Config.setSrc();

// 		Params.parse(args);


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
// 	}
// }
