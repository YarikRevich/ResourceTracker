package com.resourcetracker.providers;

import com.resourcetracker.Constants;
import com.resourcetracker.entity.AWSResult;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.services.TerraformAPIService;
import com.resourcetracker.wrapper.ECSTaskRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

/**
 * AWS implementation of Provider
 *
 * @author YarikRevich
 *
 */
public class AWS implements IProvider {
	final static Logger logger = LogManager.getLogger(AWS.class);

	TerraformAPIService terraformAPIService;

	public void setTerraformAPIService(TerraformAPIService terraformAPIService) {
		this.terraformAPIService = terraformAPIService;
	}

	public URL start() {
		ConfigEntity configEntity = terraformAPIService.getConfigEntity();
		ConfigEntity.Cloud cloud = configEntity.getCloud();

		terraformAPIService.setEnvVar("AWS_SHARED_CREDENTIALS_FILE", cloud.getCredentials());
		terraformAPIService.setEnvVar("AWS_PROFILE", cloud.getProfile());
		terraformAPIService.setEnvVar("AWS_REGION", cloud.getRegion());

		terraformAPIService.setVar(Constants.TERRAFORM_CONTEXT_ENV_VAR, terraformAPIService.getContext());
		terraformAPIService.setVar(Constants.TERRAFORM_SHARED_CREDENTIALS_FILE_ENV_VAR, terraformAPIService.getCredentials());
		terraformAPIService.setBackendConfig(Constants.TERRAFORM_BACKEND_CONFIG_SHARED_CREDENTIALS_FILE, terraformAPIService.getCredentials());

		terraformAPIService.apply();
		AWSResult result = terraformAPIService.<AWSResult>getResult();
		ECSTaskRunner ecsTaskRunner = new ECSTaskRunner(result);
		return ecsTaskRunner.run();
	}

	public void stop() {
		terraformAPIService.destroy();
	}
}
