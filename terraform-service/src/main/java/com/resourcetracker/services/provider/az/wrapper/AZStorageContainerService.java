package com.resourcetracker.services.provider.az.wrapper;

public class AZStorageContainerService {
	public void run(){
		try {
			azureResourceManager.storageBlobServices().define("resourcetracker-bd")
					.withExistingStorageAccount(resourceGroup, storageAccount).create().wait();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
