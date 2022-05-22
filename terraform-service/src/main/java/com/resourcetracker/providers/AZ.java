package com.resourcetracker.terraform.providers;

import com.resourcetracker.cloud.Provider;

import java.net.InetAddress;
import org.javatuples.*;
import com.resourcetracker.terraform.common.TerraformAPI;

public class AZ implements IProvider {
	private TerraformAPI terraformAPI = new TerraformAPI();

	@Override
	public void start(String context) {
		terraformAPI.setVar("context", Config.formatContext());
		terraformAPI.setEnvVar("ARM_CLIENT_ID", "");
		terraformAPI.setEnvVar("ARM_CLIENT_SECRET", "");
		terraformAPI.setEnvVar("ARM_SUBSCRIPTION_ID", "");
		terraformAPI.setEnvVar("ARM_TENANT_ID", "");

		terraformAPI.start();

		if (terraformAPI.isError()) {
			logger.error("AZ tracker is not started..");
		} else {
			logger.info("AZ tracker is started!");
		}
	};

	@Override
	public void stop() {
		terraformAPI.stop();
	};
}
