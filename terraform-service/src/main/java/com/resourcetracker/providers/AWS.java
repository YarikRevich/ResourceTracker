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
import java.util.List;

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

	private void selectEnvVars(){
		terraformAPIService.setEnvVar(Constants.AWS_SHARED_CREDENTIALS_FILE, terraformAPIService.getCredentials());
		terraformAPIService.setEnvVar(Constants.AWS_PROFILE, terraformAPIService.getProfile());
		terraformAPIService.setEnvVar(Constants.AWS_REGION, terraformAPIService.getRegion());
//		terraformAPIService.setEnvVar(Constants.AWS_SDK_LOAD_CONFIG, Constants.AWS_SDK_LOAD_CONFIG_VALUE);
	}

	private void selectVars(){
		terraformAPIService.setVar(Constants.TERRAFORM_CONTEXT_ENV_VAR, terraformAPIService.getContext());
		terraformAPIService.setVar(Constants.TERRAFORM_SHARED_CREDENTIALS_FILE_ENV_VAR, terraformAPIService.getCredentials());
	}

	private void selectBackendConfig(){
		terraformAPIService.setBackendConfig(Constants.TERRAFORM_BACKEND_CONFIG_SHARED_CREDENTIALS_FILE, terraformAPIService.getCredentials());
		terraformAPIService.setBackendConfig(Constants.TERRAFORM_BACKEND_PROFILE, terraformAPIService.getProfile());
	}

	public URL start() {
		this.selectEnvVars();
		this.selectVars();
		this.selectBackendConfig();

		terraformAPIService.apply();

		AWSResult result = AWSResult.fromJson(terraformAPIService.getResult());
		ECSTaskRunner ecsTaskRunner = new ECSTaskRunner(result);
		return ecsTaskRunner.run();
	}

	public void stop() {
		this.selectEnvVars();
		this.selectVars();
		this.selectBackendConfig();

		terraformAPIService.destroy();
	}
}
