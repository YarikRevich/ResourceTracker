package com.resourcetracker.entity;

import java.util.List;

public class StateEntity {
	public enum Mode{
		STARTED("started"),
		STOPED("stoped");

		private String mode;
		private Mode(String mode){
			this.mode = mode;
		}
	}

	public static class State{
		String project;

		Mode mode;

		public String getProject() {
			return project;
		}

		public Mode getMode() {
			return mode;
		}

		public void setProject(String project) {
			this.project = project;
		}

		public void setMode(Mode mode) {
			this.mode = mode;
		}
	}

	public List<State> states;

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	//	public void setMode(Mode mode) {
//		this.mode = mode;
//	}

//	public Mode getMode() {
//		return mode;
//	}
	public int configFileHash;

	public int getConfigFileHash() {
		return configFileHash;
	}

	public void setConfigFileHash(int configFileHash) {
		this.configFileHash = configFileHash;
	}


	public String kafkaBootstrapServer;

	public void setKafkaBootstrapServer(String kafkaBootstrapServer) {
		this.kafkaBootstrapServer = kafkaBootstrapServer;
	}

	public String getKafkaBootstrapServer() {
		return kafkaBootstrapServer;
	}
}
