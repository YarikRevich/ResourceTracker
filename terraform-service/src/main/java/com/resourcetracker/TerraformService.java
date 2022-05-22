package com.resourcetracker;

import org.javatuples.Pair;

import com.resourcetracker.ConfigService;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ConfigException;

import com.resourcetracker.providers.common.IProvider;

public class TerraformService {
	private IProvider chosenProvider;

	public TerraformService(ConfigEntity.Provider provider) {
		try {
			switch (provider) {
				case AWS:
					chosenProvider = new AWS();
				case GCP:
					chosenProvider = new GCP();
				case AZ:
					chosenProvider = new AZ();
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
