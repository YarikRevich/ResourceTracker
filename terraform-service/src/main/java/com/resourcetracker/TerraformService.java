package com.resourcetracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.services.api.TerraformAPI;
import com.resourcetracker.services.provider.AWS;
import com.resourcetracker.services.provider.AZ;
import com.resourcetracker.services.provider.GCP;
import com.resourcetracker.services.provider.common.IProvider;

@Service
@Import({ TerraformAPI.class })
public class TerraformService {
	private static final Logger logger = LogManager.getLogger(TerraformService.class);

	@Autowired
	TerraformAPI terraformAPIService;

	private IProvider chosenProvider;

	private ConfigEntity configEntity;

	public void setConfigEntity(ConfigEntity configEntity) {
		this.configEntity = configEntity;
	}

	private void selectProvider() {
		switch (this.configEntity.getCloud().getProvider()) {
			case AWS:
				chosenProvider = new AWS();
				break;
			case GCP:
				chosenProvider = new GCP();
				break;
			case AZ:
				chosenProvider = new AZ();

			// default:
			// 	logger.fatal("");
			// 	shutdownManager.initiateShutdown(1);
		}

		terraformAPIService.setConfigEntity(configEntity);
		chosenProvider.setTerraformAPIService(terraformAPIService);
	}

	/**
	 * Starts remote execution on a chosen provider
	 *
	 * @return URL endpoint to the remote resources where execution is
	 *         going
	 */
	public String start() {
		this.selectProvider();
		return chosenProvider.start();
	}

	public void stop() {
		this.selectProvider();
		chosenProvider.stop();
	}
}
