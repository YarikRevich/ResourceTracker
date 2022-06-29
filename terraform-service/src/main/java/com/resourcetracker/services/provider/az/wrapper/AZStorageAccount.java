package com.resourcetracker.services.provider.az.wrapper;

import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.AzureAuthorityHosts;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.storage.models.PublicAccess;

public class AZStorageAccount {

	public AZStorageAccount() {
	}

	public AZStorageAccountResult run() {
		String storageAccount = azureResourceManager.storageAccounts().define("resourcegroup-bd").withRegion("")
				.withExistingResourceGroup(resourceGroup).create().name();
	}
}
