package com.resourcetracker;

public class Constants {
//	public static final String PID_FILE_PATH = "/usr/local/var/run/resourcetracker.pid";
	public static final String HOME_FOLDER_PATH = "/usr/local/etc/resourcetracker/";
	public static final String CONFIG_FILE_PATH = HOME_FOLDER_PATH.concat("resourcetracker.yaml");

	/**
	 * Represent file which are used by state manager
	 */
	public static final String STATE_FILE_PATH = HOME_FOLDER_PATH.concat(".state");

	/**
	 * Terraform
	 */
	public static final String TERRAFORM_CONFIG_FILES_PATH = HOME_FOLDER_PATH.concat("tf");

	public static final String TERRAFORM_CONTEXT_ENV_VAR = "resourcetracker_context";
	public static final String TERRAFORM_SHARED_CREDENTIALS_FILE_ENV_VAR = "resourcetracker_shared_credentials_file";

	public static final String TERRAFORM_BACKEND_CONFIG_SHARED_CREDENTIALS_FILE = "shared_credentials_file";
	/**
	 * Configuration for Kafka
	 */
	public static final String KAFKA_STATUS_TOPIC = "status";
}
