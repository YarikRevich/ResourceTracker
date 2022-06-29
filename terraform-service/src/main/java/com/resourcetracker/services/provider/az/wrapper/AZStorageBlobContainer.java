package com.resourcetracker.services.provider.az.wrapper;

import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.storage.models.BlobContainer;
import com.azure.resourcemanager.storage.models.PublicAccess;
import com.resourcetracker.Constants;
import com.resourcetracker.services.provider.az.wrapper.entity.AZResourceGroupResult;
import com.resourcetracker.services.provider.az.wrapper.entity.AZResourceManagerResult;
import com.resourcetracker.services.provider.az.wrapper.entity.AZStorageAccountResult;
import com.resourcetracker.services.provider.az.wrapper.entity.AZStorageBlobContainerResult;

public class AZStorageBlobContainer {

	AZResourceManagerResult azResourceManagerResult;
	AZStorageAccountResult azStorageAccountResult;
	AZResourceGroupResult azResourceGroupResult;

	public AZStorageBlobContainer(AZResourceGroupResult azResourceGroupResult,
			AZStorageAccountResult azStorageAccountResult, AZResourceManagerResult azResourceManagerResult) {
		this.azResourceGroupResult = azResourceGroupResult;
		this.azStorageAccountResult = azStorageAccountResult;
		this.azResourceManagerResult = azResourceManagerResult;
	}

	public AZStorageBlobContainerResult run() {
		AZStorageBlobContainerResult azStorageBlobContainerResult = new AZStorageBlobContainerResult();

		AzureResourceManager azResourceManager = azResourceManagerResult.getResourceManager();

		BlobContainer blobContainer = azResourceManager.storageBlobContainers()
				.defineContainer(Constants.AZ_CONTAINER_NAME)
				.withExistingBlobService(azResourceGroupResult.getResourceGroupName(),
						azStorageAccountResult.getStorageAccountName())
				.withPublicAccess(PublicAccess.BLOB)
				.create();

		try {
			blobContainer.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return azStorageBlobContainerResult;
	}
}
