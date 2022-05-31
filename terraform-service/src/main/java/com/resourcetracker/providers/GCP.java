package com.resourcetracker.providers;

import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.services.TerraformAPIService;

import org.springframework.beans.factory.annotation.Autowired;

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

	public void start(String context) {
		terraformAPIService.setEnvVar("GOOGLE_CREDENTIALS", "");
		terraformAPIService.setEnvVar("GOOGLE_PROJECT", "");
		terraformAPIService.setEnvVar("GOOGLE_REGION", "");
		terraformAPIService.setEnvVar("GOOGLE_ZONE", "");

		terraformAPIService.setVar("RESOURCETRACKER_CONTEXT", context);

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
