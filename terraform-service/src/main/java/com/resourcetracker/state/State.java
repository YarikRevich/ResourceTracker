package com.resourcetracker.state;

import java.io.File;

import com.resourcetracker.Constants;

import java.io.File;
import java.io.FileWriter;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * State of terraform service
 */
public class State {
	private static final Logger logger = LogManager.getLogger(ConfigService.class);

	/**
	 * Checks if '.started' file exists in ResourceTracker
	 * home project and thus checks if it was started
	 * @return if ResourceTracker was started
	 */
	public boolean isStarted() {
		File startedFile = new File(Constants.STARTED_STATE_FILE_PATH);
		return startedFile.exists();
	};

	public void setStarted(){
		File startedFile = new File(Constants.STARTED_STATE_FILE_PATH);
		if (!startedFile.createNewFile()){
			logger.error("state '.started' file was not created successfuly");
		}
		File configFile = new File(Constants.CONFIG_FILE_PATH);
		FileWriter startedFileWriter = new FileWriter(startedFile);
		startedFileWriter.write(configFile.hashCode());
		startedFileWriter.close();
	}
}
