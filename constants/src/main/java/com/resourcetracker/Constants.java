package com.resourcetracker;

public class Constants {
	public static final String PID_FILE_PATH = "/usr/local/var/run/resourcetracker.pid";

	public static final String HOME_FOLDER_PATH = "/usr/local/etc/resourcetracker/";

	public static final String CONFIG_FILE_PATH = HOME_FOLDER_PATH.concat("resourcetracker.yaml");

	/**
	 * Represent file which are used by state manager
	 */
	public static final String STATE_FILE_PATH = HOME_FOLDER_PATH.concat(".state");
}
