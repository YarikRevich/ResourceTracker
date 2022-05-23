package com.resourcetracker;

import org.javatuples.Pair;

import com.resourcetracker.ConfigService;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ConfigException;

import com.resourcetracker.providers.common.IProvider;

import com.resourcetracker.common.TerraformAPI;

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
		chosenProvider.setTerraformAPI(new TerraformAPI());
	}

	public void start(String context) {
		chosenProvider.start(context);
	}

	public void stop() {
		chosenProvider.stop();
	}
}
