package com.resourcetracker.providers;

import com.resourcetracker.Constants;
import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.services.TerraformAPIService;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.URL;
import java.util.Optional;

public class AZ implements IProvider {
	final static Logger logger = LogManager.getLogger(AZ.class);

	@Autowired
	private TerraformAPIService terraformAPIService;

	public URL start(String context) {
		terraformAPIService.setEnvVar("ARM_CLIENT_ID", "");
		terraformAPIService.setEnvVar("ARM_CLIENT_SECRET", "");
		terraformAPIService.setEnvVar("ARM_SUBSCRIPTION_ID", "");
		terraformAPIService.setEnvVar("ARM_TENANT_ID", "");

		terraformAPIService.setVar("RESOURCETRACKER_CONTEXT", context);

		URL publicEndpoint = terraformAPIService.start(Optional.of(Constants.PATH_TO_AZ_PROVIDER_TERRAFORM_CONFIGURATION));
		if (publicEndpoint != null) {
			logger.error(String.format("Provider(%s) is started", this.getClass().toString()));
		} else {
			logger.error(String.format("Provider(%s) is not started", this.getClass().toString()));
		}
		return publicEndpoint;
	};

	public void stop() {
		terraformAPIService.stop();
	};
}
