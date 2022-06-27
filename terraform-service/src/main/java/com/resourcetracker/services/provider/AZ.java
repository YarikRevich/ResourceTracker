package com.resourcetracker.services.provider;

import com.resourcetracker.Constants;
import com.resourcetracker.services.api.TerraformAPI;
import com.resourcetracker.services.provider.common.IProvider;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.net.URL;
import java.util.Optional;

public class AZ implements IProvider {
	final static Logger logger = LogManager.getLogger(AZ.class);


	TerraformAPI terraformAPIService;
	public void setTerraformAPIService(TerraformAPI terraformAPIService) {
		this.terraformAPIService = terraformAPIService;
	}

	public String start() {
		terraformAPIService.setEnvVar("ARM_CLIENT_ID", "");
		terraformAPIService.setEnvVar("ARM_CLIENT_SECRET", "");
		terraformAPIService.setEnvVar("ARM_SUBSCRIPTION_ID", "");
		terraformAPIService.setEnvVar("ARM_TENANT_ID", "");

		terraformAPIService.setVar("context", terraformAPIService.getContext());

//		URL publicEndpoint = terraformAPIService.apply();
//		if (publicEndpoint != null) {
//			logger.error(String.format("Provider(%s) is started", this.getClass().toString()));
//		} else {
//			logger.error(String.format("Provider(%s) is not started", this.getClass().toString()));
//		}
//		return publicEndpoint;
		return null;
	};

	public void stop() {
		terraformAPIService.destroy();
	};
}
