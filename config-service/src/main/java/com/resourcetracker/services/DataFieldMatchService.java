package com.resourcetracker.services;

import java.util.regex.Pattern;

import com.resourcetracker.exception.DataFieldException;

/**
 * Class used for matching information
 * stated in 'data' field in configuration file
 */
public class DataFieldMatchService {
	public static enum DataFieldType {
		NONE,
		FILE,
		SCRIPT;
	}

	/**
	 * Detects if passed information to 'data' filed
	 * is appropriate for available enum structure
	 *
	 * @param data information passed to 'data' field in configuration file
	 * @return enum value matched by pattern
	 */
	public static DataFieldType matches(String data) {
		if (Pattern.matches("^(((~./)?)|((~/.)?)|((/?))?)([a-zA-Z/]*)((\\.([a-z]+))?)$", data)) {
			return DataFieldType.FILE;
		} else {
			return DataFieldType.SCRIPT;
		}
	}
}
