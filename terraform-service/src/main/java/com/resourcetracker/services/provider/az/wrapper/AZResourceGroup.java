package com.resourcetracker.services.provider.az.wrapper;

import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.resources.models.ResourceGroup;
import com.resourcetracker.services.provider.az.wrapper.entity.AZResourceManagerResult;
import com.resourcetracker.Constants;
import com.resourcetracker.services.provider.az.wrapper.entity.AZResourceGroupResult;

public class AZResourceGroup {
	private AZResourceManagerResult azResourceManagerResult;

	public AZResourceGroup(AZResourceManagerResult azResourceManagerResult) {
		this.azResourceManagerResult = azResourceManagerResult;
	}

	public AZResourceGroupResult run() {
		AZResourceGroupResult azResourceGroupResult = new AZResourceGroupResult();

		AzureResourceManager azResourceManager = azResourceManagerResult.getResourceManager();
		ResourceGroup resourceGroup = azResourceManager.resourceGroups()
				.define(Constants.AZ_RESOURCE_GROUP_NAME)
				.withRegion("")
				.create();
		try {
			resourceGroup.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		azResourceGroupResult.setResourceGroupName(resourceGroup.name());

		return azResourceGroupResult;
	}
}
