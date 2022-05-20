package com.resourcetracker.terraform.providers;

import com.resourcetracker.cloud.Provider;
import com.resourcetracker.config.Config;
import com.resourcetracker.terraform.common.TerraformAPI;

import java.net.InetAddress;
import org.javatuples.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

/**
 * AWS implementation of Provider
 *
 * @author YarikRevich
 *
 */
public class AWS implements IProvider {
	final static Logger logger = LogManager.getLogger(AWS.class);

	private TerraformAPI terraformAPI = new TerraformAPI();

	@Override
	public void start() {
		terraformAPI.setVar("context", Config.formatContext());

		terraformAPI.setEnvVar("AWS_SHARED_CREDENTIALS_FILE", "");
		terraformAPI.setEnvVar("AWS_REGION", "");
		terraformAPI.setEnvVar("AWS_PROFILE", "");

		terraformAPI.start();

		if (terraformAPI.isError()) {
			logger.error("AWS tracker is not started..");
		} else {
			logger.info("AWS tracker is started!");
		}
	}

	@Override
	public void stop() {
		terraformAPI.stop();
	}
}
