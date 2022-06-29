package com.resourcetracker.services.provider.az.wrapper;

import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.resources.models.ResourceGroup;
import com.resourcetracker.services.provider.az.wrapper.entity.AZResourceManagerResult;
import com.resourcetracker.services.provider.az.wrapper.entity.AZResourceGroupResult;

public class AZResourceGroup {
	private AZResourceManagerResult azResourceManagerResult;

	public AZResourceGroup(AZResourceManagerResult azResourceManagerResult) {
		this.azResourceManagerResult = azResourceManagerResult;
	}

	public AZResourceGroupResult run() {
		AZResourceGroupResult azResourceManagerResult = new AZResourceGroupResult();

		AzureResourceManager azResourceManager = azResourceManagerResult.getResourceManager();
		ResourceGroup resourceGroup = azResourceManager.resourceGroups()
				.define("resourcetracker")
				.withRegion("")
				.create();
		resourceGroup.wait();

		azResourceManagerResult.setResourceGroupName(resourceGroup.name());

		return azResourceManagerResult;
	}
}
