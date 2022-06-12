package com.resourcetracker.providers;

import com.resourcetracker.Constants;
import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.services.TerraformAPIService;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.URL;
import java.util.Optional;

/**
 * @author YarikRevich
 *
 */
public class GCP implements IProvider {
	final static Logger logger = LogManager.getLogger(GCP.class);

	TerraformAPIService terraformAPIService;

	public void setTerraformAPIService(TerraformAPIService terraformAPIService) {
		this.terraformAPIService = terraformAPIService;
	}

	public URL start() {
		terraformAPIService.setEnvVar("GOOGLE_CREDENTIALS", "");
		terraformAPIService.setEnvVar("GOOGLE_PROJECT", "");
		terraformAPIService.setEnvVar("GOOGLE_REGION", "");
		terraformAPIService.setEnvVar("GOOGLE_ZONE", "");

		terraformAPIService.setVar("context", terraformAPIService.getContext());

		terraformAPIService.setDirectory(terraformAPIService.getProvider());
		URL publicEndpoint = terraformAPIService.apply();
		if (publicEndpoint != null) {
			logger.error(String.format("Provider(%s) is started", this.getClass().toString()));
		} else {
			logger.error(String.format("Provider(%s) is not started", this.getClass().toString()));
		}
		return publicEndpoint;
	};

	public void stop() {
		terraformAPIService.destroy();
	};
}
