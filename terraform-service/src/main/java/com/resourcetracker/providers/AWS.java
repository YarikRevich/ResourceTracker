package com.resourcetracker.providers;

import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.services.TerraformAPIService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

// import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import org.springframework.beans.factory.annotation.Autowired;

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

	public void start() {
		// terraformAPIService.setVar("context", Config.formatContext());

		terraformAPIService.setEnvVar("AWS_SHARED_CREDENTIALS_FILE", "");
		terraformAPIService.setEnvVar("AWS_REGION", "");
		terraformAPIService.setEnvVar("AWS_PROFILE", "");

		if (terraformAPIService.start()) {
			logger.error(String.format("Provider(%s) is started", this.getClass().toString()));
		} else {
			logger.error(String.format("Provider(%s) is not started", this.getClass().toString()));
		}
	}

	public void stop() {
		if (terraformAPIService.stop()){
			logger.error(String.format("Provider(%s) is stoped", this.getClass().toString()));
		}else{
			logger.error(String.format("Provider(%s) is not stoped", this.getClass().toString()));
		}
	}
}
