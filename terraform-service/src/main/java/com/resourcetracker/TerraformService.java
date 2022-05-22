package com.resourcetracker;

import org.javatuples.Pair;

import com.resourcetracker.config.*;
import com.resourcetracker.cloud.providers.*;
import com.resourcetracker.tools.exceptions.ConfigException;
import com.resourcetracker.config.Config;

public class TerraformService {
	private Provider provider;
	public Config config;

	public TerraformService(Config config) {
		try {
			var parsedConfigFile = config .getParsedConfigFile();
			switch (parsedConfigFile.cloud.provider) {
				case AWS:
					provider = new AWS();
				case GCP:
					provider = new GCP();
				case AZ:
					provider = new AZ();
			}
		} catch (ConfigException e) {
			e.printStackTrace();
		}
	}

	public void start(String context) {
		provider.start(context);
	}

	public void stop() {
		provider.stop();
	}

	public static boolean isOk() {
		return false;
	}
}
