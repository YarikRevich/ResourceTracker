package com.resourcetracker.terraform.providers;

import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.terraform.services.TerraformAPIService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AZ implements IProvider {
	final static Logger logger = LogManager.getLogger(AZ.class);

	@Autowired
	private TerraformAPIService terraformAPIService;

	@Override
	public void start(String context) {
		// terraformAPI.setVar("context", Config.formatContext());

		terraformAPI.setEnvVar("ARM_CLIENT_ID", "");
		terraformAPI.setEnvVar("ARM_CLIENT_SECRET", "");
		terraformAPI.setEnvVar("ARM_SUBSCRIPTION_ID", "");
		terraformAPI.setEnvVar("ARM_TENANT_ID", "");

		terraformAPI.start();

		if (terraformAPI.isError()) {
			logger.error("AZ tracker is not started..");
		} else {
			logger.info("AZ tracker is started!");
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
