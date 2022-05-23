package com.resourcetracker.terraform.providers;

import com.resourcetracker.providers.common.IProvider;

import com.resourcetracker.terraform.services.TerraformAPIService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author YarikRevich
 *
 */
public class GCP implements IProvider {
	final static Logger logger = LogManager.getLogger(GCP.class);

	@Autowired
	private TerraformAPIService terraformAPIService;

	@Override
	public void start() {
		// terraformAPI.setVar("context", Config.formatContext());

		terraformAPI.setEnvVar("GOOGLE_CREDENTIALS", "");
		terraformAPI.setEnvVar("GOOGLE_PROJECT", "");
		terraformAPI.setEnvVar("GOOGLE_REGION", "");
		terraformAPI.setEnvVar("GOOGLE_ZONE", "");

		terraformAPI.start();

		if (terraformAPI.isError()) {
			logger.error("GCP tracker is not started..");
		} else {
			logger.info("GCP tracker is started!");
		}
	};

	@Override
	public void stop() {
		terraformAPI.stop();
	};

	@Override
	public void setTerraformAPI(TerraformAPI terraformAPI) {
		this.terraformAPI = terraformAPI;
	}
}
