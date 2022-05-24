package com.resourcetracker;

import java.io.File;

import com.resourcetracker.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.resourcetracker.entity.StateEntity;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.exc.StreamWriteException;

/**
 * State of terraform service
 */
public class StateService {
	private static final Logger logger = LogManager.getLogger(StateService.class);

	private static StateEntity parsedStateFile = null;
	private static ObjectMapper objectMapper = null;

	public StateService() {
		if (parsedStateFile == null) {
			objectMapper = objectMapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			File stateFile = new File(Constants.STATE_FILE_PATH);
			try {
				if (stateFile.createNewFile()) {
					try {
						objectMapper.writeValue(stateFile, this.getDefaultStateEntity());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				parsedStateFile = objectMapper.readValue(stateFile, StateEntity.class);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return default StateEntity entity
	 */
	private StateEntity getDefaultStateEntity() {
		StateEntity stateEntry = new StateEntity();
		stateEntry.mode = StateEntity.Mode.STOPED;
		File configFile = new File(Constants.CONFIG_FILE_PATH);
		stateEntry.configFileHash = configFile.hashCode();
		return stateEntry;
	}

	/**
	 * Checks if state file is actual.
	 * Means, if hash code of configuration file is same as
	 * hash code recorded to '.state' file
	 *
	 * @return
	 */

	/**
	 * Checks if '.started' file exists in ResourceTracker
	 * home project and thus checks if it was started
	 *
	 * @return if ResourceTracker was started
	 */

	/**
	 * Checks if ResourceTracker is stoped
	 *
	 * @return
	 */

	public boolean isMode(StateEntity.Mode mode) {
		return parsedStateFile.mode == mode;
	}

	public void setMode(StateEntity.Mode mode) {
		parsedStateFile.mode = mode;
		try {
			objectMapper.writeValue(new File(Constants.STATE_FILE_PATH), parsedStateFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConfigFileHashActual() {
		File configFile = new File(Constants.CONFIG_FILE_PATH);
		return parsedStateFile.configFileHash == configFile.hashCode();
	}

	public void actualizeConfigFileHash() {
		File configFile = new File(Constants.CONFIG_FILE_PATH);
		parsedStateFile.configFileHash = configFile.hashCode();
		try {
			objectMapper.writeValue(new File(Constants.STATE_FILE_PATH), parsedStateFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
