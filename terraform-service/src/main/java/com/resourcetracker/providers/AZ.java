package com.resourcetracker.providers;

import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.services.TerraformAPIService;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class AZ implements IProvider {
	final static Logger logger = LogManager.getLogger(AZ.class);

	@Autowired
	private TerraformAPIService terraformAPIService;

	public void start() {
		// terraformAPIService.setVar("context", Config.formatContext());

		terraformAPIService.setEnvVar("ARM_CLIENT_ID", "");
		terraformAPIService.setEnvVar("ARM_CLIENT_SECRET", "");
		terraformAPIService.setEnvVar("ARM_SUBSCRIPTION_ID", "");
		terraformAPIService.setEnvVar("ARM_TENANT_ID", "");

		if (terraformAPIService.start()) {
			logger.error(String.format("Provider(%s) is started", this.getClass().toString()));
		} else {
			logger.error(String.format("Provider(%s) is not started", this.getClass().toString()));
		}
	};

	public void stop() {
		terraformAPIService.stop();
	};
}
