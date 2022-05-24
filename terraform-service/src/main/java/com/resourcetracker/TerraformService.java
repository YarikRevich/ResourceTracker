package com.resourcetracker;

import com.resourcetracker.ConfigService;
import com.resourcetracker.entity.ConfigEntity;

import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.providers.AWS;
import com.resourcetracker.providers.AZ;
import com.resourcetracker.providers.GCP;

public class TerraformService {
	private IProvider chosenProvider;

	public TerraformService(ConfigEntity.Provider provider) {
		switch (provider) {
			case AWS:
				chosenProvider = new AWS();
			case GCP:
				chosenProvider = new GCP();
			case AZ:
				chosenProvider = new AZ();
		}
	}

	public void start(String context) {
		chosenProvider.start();
	}

	public void stop() {
		chosenProvider.stop();
	}
}
