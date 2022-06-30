package com.resourcetracker.services.provider.az.wrapper;

import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.storage.models.BlobServiceProperties;
import com.azure.resourcemanager.storage.models.BlobServices;
import com.resourcetracker.Constants;
import com.resourcetracker.services.provider.az.wrapper.entity.AZResourceManagerResult;
import com.resourcetracker.services.provider.az.wrapper.entity.AZStorageContainerServiceResult;

public class AZStorageContainerService {
	AZResourceManagerResult azResourceManagerResult;

	public AZStorageContainerService(AZResourceManagerResult azResourceManagerResult) {
		this.azResourceManagerResult = azResourceManagerResult;
	}

	public AZStorageContainerServiceResult run() {
		AZStorageContainerServiceResult azStorageBlobContainerResult = new AZStorageContainerServiceResult();

		AzureResourceManager azResourceManager = azResourceManagerResult.getResourceManager();

		BlobServiceProperties blobServiceProperties = azResourceManager.storageBlobServices()
				.define(Constants.AZ_CONTAINER_SERVICE_NAME)
				.withExistingStorageAccount("", "")
				.create();
		try {
			blobServiceProperties.wait();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		return azStorageBlobContainerResult;
	}
}
