package com.resourcetracker.services.provider.az.wrapper;

public class AZStorageBlobContainer {
	public void run(){
		try {
			azureResourceManager.storageBlobContainers().defineContainer("tfstate")
					.withExistingBlobService(resourceGroup, storageAccount).withPublicAccess(PublicAccess.BLOB).create()
					.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
