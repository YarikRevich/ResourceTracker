package com.resourcetracker.services.provider.aws;

import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.resourcetracker.Constants;
import com.resourcetracker.services.api.TerraformAPI;
import com.resourcetracker.services.provider.aws.entity.AWSResult;
import com.resourcetracker.services.provider.aws.wrapper.ECSDescribeNetworkInterfaces;
import com.resourcetracker.services.provider.aws.wrapper.ECSDescribeTask;
import com.resourcetracker.services.provider.aws.wrapper.ECSListTasks;
import com.resourcetracker.services.provider.aws.wrapper.entity.ECSDescribeNetworkInterfacesResult;
import com.resourcetracker.services.provider.aws.wrapper.entity.ECSDescribeTaskResult;
import com.resourcetracker.services.provider.aws.wrapper.entity.ECSListTasksResult;
import com.resourcetracker.services.provider.common.IProvider;

/**
 * AWS implementation of Provider
 *
 * @author YarikRevich
 *
 */
public class AWS implements IProvider {
	final static Logger logger = LogManager.getLogger(AWS.class);

	TerraformAPI terraformAPIService;

	public void setTerraformAPIService(TerraformAPI terraformAPIService) {
		this.terraformAPIService = terraformAPIService;
	}

	private void selectEnvVars(){
		var configEntity = this.terraformAPIService.getConfigEntity();

		terraformAPIService.setEnvVar(Constants.AWS_SHARED_CREDENTIALS_FILE, configEntity.getCloud().getCredentials());
		terraformAPIService.setEnvVar(Constants.AWS_PROFILE, configEntity.getCloud().getProfile());
		terraformAPIService.setEnvVar(Constants.AWS_REGION, configEntity.getCloud().getRegion());
	}

	private void selectVars(){
		var configEntity = this.terraformAPIService.getConfigEntity();

		terraformAPIService.setVar(Constants.TERRAFORM_CONTEXT_VAR, configEntity.toJSONAsContext());
	}

	private void selectBackendConfig(){
		var configEntity = this.terraformAPIService.getConfigEntity();

		terraformAPIService.setBackendConfig(Constants.TERRAFORM_BACKEND_CONFIG_SHARED_CREDENTIALS_FILE, configEntity.getCloud().getCredentials());
		terraformAPIService.setBackendConfig(Constants.TERRAFORM_BACKEND_PROFILE, configEntity.getCloud().getProfile());
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
