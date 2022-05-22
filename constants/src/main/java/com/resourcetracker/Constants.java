package com.resourcetracker;

public class Constants {
	public static final String HOME_FOLDER_PATH = "/usr/local/etc/resourcetracker";

	public static final String CONFIG_FILE_PATH = Strings.join(HOME_FOLDER_PATH, "resourcetracker.yaml");
	public static final String STARTED_STATE_FILE_PATH = Strings.join(HOME_FOLDER_PATH, ".started");
}
