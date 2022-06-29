package com.resourcetracker.services.provider.az.wrapper;

import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.AzureAuthorityHosts;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import com.resourcetracker.services.provider.az.wrapper.entity.AZResourceManagerResult;

public class AZResourceManager {
	private String clientId;
	private String clientSecret;
	private String tenantId;
	private String subscriptionId;

	public AZResourceManager(String clientId, String clientSecret, String tenantId, String subscriptionId) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.tenantId = tenantId;
		this.subscriptionId = subscriptionId;
	}

	public AZResourceManagerResult run() {
		AZResourceManagerResult azResourceManagerResult = new AZResourceManagerResult();

		ClientSecretCredential credential = new ClientSecretCredentialBuilder()
				.clientId(clientId)
				.clientSecret(clientSecret)
				.tenantId(tenantId)
				.authorityHost(AzureAuthorityHosts.AZURE_PUBLIC_CLOUD)
				.build();

		AzureProfile profile = new AzureProfile(AzureEnvironment.AZURE);

		azResourceManagerResult.setResourceManager(AzureResourceManager.configure()
				.withLogLevel(HttpLogDetailLevel.BASIC)
				.authenticate(credential, profile)
				.withSubscription(subscriptionId));

		return azResourceManagerResult;
	}
}
