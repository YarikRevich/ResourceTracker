package com.resourcetracker.cloud.providers;

import com.resourcetracker.cloud.Provider;

import java.net.InetAddress;
import org.javatuples.*;

public class AZ implements Provider {
	private TF tf = new TF();

	@Override
	public void start(String context) {
		tf.setVar("context", Config.formatContext());

		tf.setEnvVar("ARM_CLIENT_ID", "");
		tf.setEnvVar("ARM_CLIENT_SECRET", "");
		tf.setEnvVar("ARM_SUBSCRIPTION_ID", "");
		tf.setEnvVar("ARM_TENANT_ID", "");

		tf.start();

		if (tf.isOk()) {
			logger.info("AZ tracker is started!");
		} else {
			logger.error("AZ tracker is not started..");
		}
	};

	@Override
	public void stop() {
		tf.stop();
	};
}
