package com.resourcetracker.entity;

public class StateEntity {
	public enum Mode{
		STARTED("started"),
		STOPED("stoped");

		private String mode;
		private Mode(String mode){
			this.mode = mode;
		}
	}

	public Mode mode;

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public Mode getMode() {
		return mode;
	}
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
