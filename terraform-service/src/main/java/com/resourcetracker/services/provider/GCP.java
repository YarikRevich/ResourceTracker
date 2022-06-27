package com.resourcetracker.services.provider;

import com.resourcetracker.Constants;
import com.resourcetracker.services.api.TerraformAPI;
import com.resourcetracker.services.provider.common.IProvider;

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

	TerraformAPI terraformAPIService;

	public void setTerraformAPIService(TerraformAPI terraformAPIService) {
		this.terraformAPIService = terraformAPIService;
	}

	private void selectEnvVars(){
		terraformAPIService.setEnvVar("GOOGLE_CREDENTIALS", "");
		terraformAPIService.setEnvVar("GOOGLE_PROJECT", "");
		terraformAPIService.setEnvVar("GOOGLE_REGION", "");
		terraformAPIService.setEnvVar("GOOGLE_ZONE", "");
	}

	private void selectVars(){
		terraformAPIService.setVar("context", terraformAPIService.getContext());
	}

	private void selectBackendConfig(){

	}

	public String start() {
		this.selectEnvVars();
		this.selectVars();
		this.selectBackendConfig();

//		terraformAPIService.setDirectory(terraformAPIService.getProvider());
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
		this.selectEnvVars();
		this.selectVars();
		this.selectBackendConfig();

		terraformAPIService.destroy();
	};
}
