package com.resourcetracker.providers;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.resourcetracker.Constants;
import com.resourcetracker.providers.common.IProvider;
import com.resourcetracker.providers.entity.AWSResult;
import com.resourcetracker.services.TerraformAPIService;
import com.resourcetracker.wrapper.ECSDescribeNetworkInterfaces;
import com.resourcetracker.wrapper.ECSDescribeTask;
import com.resourcetracker.wrapper.ECSListTasks;
import com.resourcetracker.wrapper.entity.ECSDescribeNetworkInterfacesResult;
import com.resourcetracker.wrapper.entity.ECSDescribeTaskResult;
import com.resourcetracker.wrapper.entity.ECSListTasksResult;

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
	}

	private void selectVars(){
		terraformAPIService.setVar(Constants.TERRAFORM_CONTEXT_ENV_VAR, terraformAPIService.getContext());
		terraformAPIService.setVar(Constants.TERRAFORM_SHARED_CREDENTIALS_FILE_ENV_VAR, terraformAPIService.getCredentials());
	}

	private void selectBackendConfig(){
		terraformAPIService.setBackendConfig(Constants.TERRAFORM_BACKEND_CONFIG_SHARED_CREDENTIALS_FILE, terraformAPIService.getCredentials());
		terraformAPIService.setBackendConfig(Constants.TERRAFORM_BACKEND_PROFILE, terraformAPIService.getProfile());
	}

	public String start() {
		this.selectEnvVars();
		this.selectVars();
		this.selectBackendConfig();

		terraformAPIService.apply();

		AWSResult result = AWSResult.fromJson(terraformAPIService.getResult());

		ECSListTasks ecsListTasks = new ECSListTasks(result);
		ECSListTasksResult ecsListTaskResult = ecsListTasks.run();

		ECSDescribeTask ecsDescribeTask = new ECSDescribeTask(result, ecsListTaskResult);
		ECSDescribeTaskResult ecsDescribeTaskResult = ecsDescribeTask.run();

		ECSDescribeNetworkInterfaces ecsDescribeNetworkInterfaces = new ECSDescribeNetworkInterfaces(ecsDescribeTaskResult);
		ECSDescribeNetworkInterfacesResult ecsDescribeNetworkInterfacesResult = ecsDescribeNetworkInterfaces.run();

		return ecsDescribeNetworkInterfacesResult.getContainerPublicIP();
	}

	public void stop() {
		this.selectEnvVars();
		this.selectVars();
		this.selectBackendConfig();

		terraformAPIService.destroy();
	}
}
