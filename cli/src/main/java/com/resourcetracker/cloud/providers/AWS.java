package com.resourcetracker.cloud.providers;

import com.resourcetracker.cloud.Provider;
import com.resourcetracker.config.Config;
import com.resourcetracker.tf.TF;

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
public class AWS implements Provider {
	final static Logger logger = LogManager.getLogger(AWS.class);

	private TF tf = new TF();

	@Override
	public void start() {
		tf.setVar("context", Config.formatContext());

		tf.setEnvVar("AWS_SHARED_CREDENTIALS_FILE", "");
		tf.setEnvVar("AWS_REGION", "");
		tf.setEnvVar("AWS_PROFILE", "");

		tf.start();

		if (tf.isOk()) {
			logger.info("AWS tracker is started!");
		} else {
			logger.error("AWS tracker is not started..");
		}
	}

	@Override
	public void stop() {
		tf.stop();
	}
}
