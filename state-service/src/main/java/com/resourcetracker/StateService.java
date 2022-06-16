package com.resourcetracker;

import java.io.File;

import com.resourcetracker.Constants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.resourcetracker.entity.StateEntity;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.exc.StreamWriteException;

import org.springframework.stereotype.Service;

/**
 * State of terraform service
 */
@Service
public class StateService {
	private static final Logger logger = LogManager.getLogger(StateService.class);

	private static StateEntity parsedStateFile;

	private static File stateFile = null;
	private static ObjectMapper stateFileObjectMapper = null;

	public StateService() {
		if (parsedStateFile == null) {
			stateFileObjectMapper = new ObjectMapper()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			stateFile = new File(Constants.STATE_FILE_PATH);
			boolean newFileCreated = false;
			if (!stateFile.exists()) {
				try {
					newFileCreated = stateFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (newFileCreated) {
				try {
					stateFileObjectMapper.writeValue(stateFile, this.getDefaultStateEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				parsedStateFile = stateFileObjectMapper.readValue(stateFile, StateEntity.class);
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
		stateEntry.setStates(new ArrayList<StateEntity.State>());
		File configFile = new File(Constants.CONFIG_FILE_PATH);
		stateEntry.setConfigFileHash(configFile.hashCode());
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

	public boolean isMode(String project, StateEntity.Mode mode) {
		List<StateEntity.State> states = parsedStateFile.getStates();
		for (StateEntity.State state : states){
			if (state.getProject().equals(project)){
				return state.getMode() == mode;
			}
		}
		return mode == StateEntity.Mode.STOPED ? true : false;
	}

	public void setMode(String project, StateEntity.Mode mode) {
		List<StateEntity.State> states = parsedStateFile.getStates();
		boolean containsProject = false;
		int projectIndex = 0;
		for (StateEntity.State state : states){
			if (state.getProject() == project){
				containsProject = true;
				projectIndex = states.indexOf(state);
			}
		}
		if (containsProject){
			states.get(projectIndex).setMode(mode);
		}else{
			StateEntity.State state = new StateEntity.State();
			state.setProject(project);
			state.setMode(mode);
			states.add(state);
		}
		parsedStateFile.setStates(states);

		try {
			stateFileObjectMapper.writeValue(stateFile, parsedStateFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConfigFileHashActual() {
		File configFile = new File(Constants.CONFIG_FILE_PATH);
		return parsedStateFile.getConfigFileHash() == configFile.hashCode();
	}

	public void actualizeConfigFileHash() {
		File configFile = new File(Constants.CONFIG_FILE_PATH);
		parsedStateFile.setConfigFileHash(configFile.hashCode());
		try {
			stateFileObjectMapper.writeValue(stateFile, parsedStateFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setKafkaBootstrapServer(String kafkaBootstrapServer){
		parsedStateFile.setKafkaBootstrapServer(kafkaBootstrapServer);
		try {
			stateFileObjectMapper.writeValue(stateFile, parsedStateFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getKafkaBootstrapServer(){
		return parsedStateFile.getKafkaBootstrapServer();
	}

	public boolean isKafkaBootstrapServer(){
		return parsedStateFile.getKafkaBootstrapServer() != null;
	}
}
