package com.resourcetracker.services.provider.az;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.resourcetracker.Constants;
import com.resourcetracker.services.api.TerraformAPI;
import com.resourcetracker.services.provider.az.wrapper.AZResourceManager;
import com.resourcetracker.services.provider.az.wrapper.entity.AZResourceManagerResult;
import com.resourcetracker.services.provider.common.IProvider;

public class AZ implements IProvider {
	final static Logger logger = LogManager.getLogger(AZ.class);

	TerraformAPI terraformAPIService;
	public void setTerraformAPIService(TerraformAPI terraformAPIService) {
		this.terraformAPIService = terraformAPIService;
	}

	public void selectEnvVars(){
		var configEntity = this.terraformAPIService.getConfigEntity();

		terraformAPIService.setEnvVar(Constants.ARM_CLIENT_ID, configEntity.getCloud().getClientId());
		terraformAPIService.setEnvVar(Constants.ARM_CLIENT_SECRET, configEntity.getCloud().getClientId());
		terraformAPIService.setEnvVar(Constants.ARM_SUBSCRIPTION_ID, configEntity.getCloud().getSubscriptionId());
		terraformAPIService.setEnvVar(Constants.ARM_TENANT_ID, configEntity.getCloud().getTenantId());
	}

	public void selectVars(){
		var configEntity = this.terraformAPIService.getConfigEntity();

		terraformAPIService.setVar(Constants.TERRAFORM_CONTEXT_VAR, configEntity.toJSONAsContext());
	}

	public void selectBackendConfig(){
		var configEntity = this.terraformAPIService.getConfigEntity();

		AZResourceManagerResult azResourceManagerResult = new AZResourceManager();

		terraformAPIService.setVar(Constants.TERRAFORM_BACKEND_STORAGE_ACCOUNT, );
		terraformAPIService.setVar(Constants.TERRAFORM_BACKEND_SUBSCRIPTION_ID, configEntity.getCloud().getSubscriptionId());
		terraformAPIService.setVar(Constants.TERRAFORM_BACKEND_TENANT_ID, configEntity.getCloud().getTenantId());
	}

	public String start() {
		this.selectEnvVars();
		this.selectVars();
		this.selectBackendConfig();

		terraformAPIService.apply();



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
