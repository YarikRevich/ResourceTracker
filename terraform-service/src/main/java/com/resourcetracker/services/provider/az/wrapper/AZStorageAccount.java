package com.resourcetracker.services.provider.az.wrapper;

import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.storage.models.StorageAccount;
import com.resourcetracker.Constants;
import com.resourcetracker.services.provider.az.wrapper.entity.AZResourceGroupResult;
import com.resourcetracker.services.provider.az.wrapper.entity.AZResourceManagerResult;
import com.resourcetracker.services.provider.az.wrapper.entity.AZStorageAccountResult;

public class AZStorageAccount {
	private AZResourceGroupResult azResourceGroupResult;
	private AZResourceManagerResult azResourceManagerResult;

	public AZStorageAccount(AZResourceGroupResult azResourceGroupResult,
			AZResourceManagerResult azResourceManagerResult) {
		this.azResourceGroupResult = azResourceGroupResult;
		this.azResourceManagerResult = azResourceManagerResult;
	}

	public AZStorageAccountResult run() {
		AZStorageAccountResult azStorageAccountResult = new AZStorageAccountResult();

		AzureResourceManager azResourceManager = azResourceManagerResult.getResourceManager();

		StorageAccount storageAccount = azResourceManager.storageAccounts()
				.define(Constants.AZ_STORAGE_ACCOUNT_NAME)
				.withRegion("")
				.withExistingResourceGroup(this.azResourceGroupResult.getResourceGroupName())
				.create();

		try {
			storageAccount.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		azStorageAccountResult.setStorageAccountName(storageAccount.name());

		return azStorageAccountResult;
	}
}
