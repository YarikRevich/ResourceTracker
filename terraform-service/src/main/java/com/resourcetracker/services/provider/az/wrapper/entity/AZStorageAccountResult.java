package com.resourcetracker.services.provider.az.wrapper.entity;

public class AZStorageAccountResult {
	String storageAccountName;

	public String getResourceManager() {
		return this.storageAccountName;
	}

	public void setResourceManager(String storageAccountName) {
		this.storageAccountName = storageAccountName;
	}

}
