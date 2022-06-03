package com.resourcetracker.providers;

import com.resourcetracker.Constants;
import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.services.TerraformAPIService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

// import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.Optional;

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

	public URL start(String context) {
		// terraformAPIService.setVar("context", Config.formatContext());

		terraformAPIService.setEnvVar("AWS_SHARED_CREDENTIALS_FILE", "");
		terraformAPIService.setEnvVar("AWS_REGION", "");
		terraformAPIService.setEnvVar("AWS_PROFILE", "");

		terraformAPIService.setVar(Constants.CONTEXT_ENV_VARIABLE_NAME, context);

		URL publicEndpoint = terraformAPIService.start(Optional.of(Constants.PATH_TO_AWS_PROVIDER_TERRAFORM_CONFIGURATION));
		if (publicEndpoint != null) {
			logger.error(String.format("Provider(%s) is started", this.getClass().toString()));
		} else {
			logger.error(String.format("Provider(%s) is not started", this.getClass().toString()));
		}
		return publicEndpoint;
	}

	public void stop() {
		if (terraformAPIService.stop()){
			logger.error(String.format("Provider(%s) is stoped", this.getClass().toString()));
		}else{
			logger.error(String.format("Provider(%s) is not stoped", this.getClass().toString()));
		}
	}
}
