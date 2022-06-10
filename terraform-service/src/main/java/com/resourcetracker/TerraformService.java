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

	private ConfigEntity configEntity;
	public void setConfigEntity(ConfigEntity configEntity){
		this.configEntity = configEntity;
	}

	public void selectProvider(){
		switch (this.configEntity.getCloud().getProvider()) {
			case AWS:
				chosenProvider = new AWS();
			case GCP:
				chosenProvider = new GCP();
			case AZ:
				chosenProvider = new AZ();
		}
	}

	/**
	 * Starts remote execution on a chosen provider
	 * @param context Context created from parsed configuration file
	 * @return URL endpoint to the remote resources where execution is
	 * going
	 */
	public URL start() {
		return chosenProvider.start(configEntity.toYAML());
	}

	public void stop() {
		chosenProvider.stop();
	}
}
