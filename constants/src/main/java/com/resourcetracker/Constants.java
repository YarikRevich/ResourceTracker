package com.resourcetracker;

public class Constants {
	public static final String HOME_FOLDER_PATH = "/usr/local/etc/resourcetracker/";
	public static final String CONFIG_FILE_PATH = HOME_FOLDER_PATH.concat("resourcetracker.yaml");
	public static final String STATE_FILE_PATH = HOME_FOLDER_PATH.concat(".state");

	/**
	 * Terraform
	 */
	public static final String TERRAFORM_CONFIG_FILES_PATH = HOME_FOLDER_PATH.concat("tf");
	public static final String TERRAFORM_CONTEXT_ENV_VAR = "resourcetracker_context";
	public static final String TERRAFORM_SHARED_CREDENTIALS_FILE_ENV_VAR = "resourcetracker_shared_credentials_file";
	public static final String TERRAFORM_BACKEND_CONFIG_SHARED_CREDENTIALS_FILE = "shared_credentials_file";
	public static final String TERRAFORM_BACKEND_PROFILE = "profile";

	/**
	 * Environment variables for AWS provider
	 */
	public static final String AWS_SHARED_CREDENTIALS_FILE = "AWS_SHARED_CREDENTIALS_FILE";
	public static final String AWS_PROFILE = "AWS_PROFILE";
	public static final String AWS_REGION = "AWS_REGION";

	/**
	 * Environment variables for GCP provider
	 */
	public static final String GCP_CREDENTIALS = "GOOGLE_CREDENTIALS";
	public static final String GCP_PROJECT = "GOOGLE_PROJECT";
	public static final String GCP_REGION = "GOOGLE_REGION";
	public static final String GCP_ZONE = "GOOGLE_ZONE";

	/**
	 * Environment variables for AZ provider
	 */

	/**
	 * Configuration for Kafka
	 */
	public static final String KAFKA_CONTAINER_NAME = "resourcetracker-kafka";
	public static final String KAFKA_STATUS_TOPIC = "status";
}
