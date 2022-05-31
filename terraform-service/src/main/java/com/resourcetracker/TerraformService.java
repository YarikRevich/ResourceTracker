package com.resourcetracker;

import com.resourcetracker.ConfigService;
import com.resourcetracker.entity.ConfigEntity;

import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.providers.AWS;
import com.resourcetracker.providers.AZ;
import com.resourcetracker.providers.GCP;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class TerraformService {
	private IProvider chosenProvider;

	public TerraformService setProvider(ConfigEntity.Provider provider){
		switch (provider) {
			case AWS:
				chosenProvider = new AWS();
			case GCP:
				chosenProvider = new GCP();
			case AZ:
				chosenProvider = new AZ();
		}
		return this;
	};

	/**
	 * Starts remote execution on a chosen provider
	 * @param context Context created from parsed configuration file
	 * @return URL endpoint to the remote resources where execution is
	 * going
	 */
	public URL start(String context) {
		return chosenProvider.start(context);
	}

	public void stop() {
		chosenProvider.stop();
	}
}
