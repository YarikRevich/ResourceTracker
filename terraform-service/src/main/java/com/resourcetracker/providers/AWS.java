package com.resourcetracker.terraform.providers;

import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.terraform.services.TerraformAPIService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

// import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import org.springframework.stereotype.Autowired;

/**
 * AWS implementation of Provider
 *
 * @author YarikRevich
 *
 */
public class AWS implements IProvider {
	final static Logger logger = LogManager.getLogger(AWS.class);

	@Autowired
	private TerraformAPIService terraformAPIService;

	@Override
	public void start() {
		// terraformAPI.setVar("context", Config.formatContext());

		terraformAPI.setEnvVar("AWS_SHARED_CREDENTIALS_FILE", "");
		terraformAPI.setEnvVar("AWS_REGION", "");
		terraformAPI.setEnvVar("AWS_PROFILE", "");

		if (terraformAPI.start()) {
			logger.error(String.format("Provider(%s) is started", this.getClass().toString()));
		} else {
			logger.error(String.format("Provider(%s) is not started", this.getClass().toString()));
		}
	}

	@Override
	public void stop() {
		if (terraformAPI.stop()){
			logger.error(String.format("Provider(%s) is stoped", this.getClass().toString()));
		}else{
			logger.error(String.format("Provider(%s) is not stoped", this.getClass().toString()));
		}
	}

	@Override
	public void setTerraformAPI(TerraformAPI terraformAPI) {
		this.terraformAPI = terraformAPI;
	}
}
