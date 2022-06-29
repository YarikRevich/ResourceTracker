package com.resourcetracker.services.provider.az.wrapper.entity;

import com.azure.resourcemanager.AzureResourceManager;

public class AZResourceManagerResult {
	AzureResourceManager resourceManager;

	public AzureResourceManager getResourceManager() {
		return this.resourceManager;
	}

	public void setResourceManager(AzureResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

}
