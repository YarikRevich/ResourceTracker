package com.resourcetracker;

public class Constants {
	public static final String PID_FILE_PATH = "/usr/local/var/run/resourcetracker.pid";

	public static final String HOME_FOLDER_PATH = "/usr/local/etc/resourcetracker/";

	public static final String CONFIG_FILE_PATH = HOME_FOLDER_PATH.concat("resourcetracker.yaml");

	/**
	 * Represent file which are used by state manager
	 */
	public static final String STATE_FILE_PATH = HOME_FOLDER_PATH.concat(".state");

	public static final String PATH_TO_AWS_PROVIDER_TERRAFORM_CONFIGURATION = "aws";
	public static final String PATH_TO_GCP_PROVIDER_TERRAFORM_CONFIGURATION = "gcp";
	public static final String PATH_TO_AZ_PROVIDER_TERRAFORM_CONFIGURATION = "az";

	public static final String CONTEXT_ENV_VARIABLE_NAME = "RESOURCETRACKER_CONTEXT";
}
