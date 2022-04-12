package com.resourcetracker.cloud.providers;

import com.resourcetracker.config.Config;
import com.resourcetracker.cloud.Provider;

import com.resourcetracker.tf.TF;

import java.net.InetAddress;
import org.javatuples.Pair;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author YarikRevich
 *
 */
public class GCP implements Provider {
	final static Logger logger = LogManager.getLogger(Loop.class);

	private TF tf = new TF();

	@Override
	public void start() {
		tf.setVar("context", Config.formatContext());

		tf.setEnvVar("GOOGLE_CREDENTIALS", "");
		tf.setEnvVar("GOOGLE_PROJECT", "");
		tf.setEnvVar("GOOGLE_REGION", "");
		tf.setEnvVar("GOOGLE_ZONE", "");

		tf.start();

		if (tf.isOk()) {
			logger.info("GCP tracker is started!");
		} else {
			logger.error("GCP tracker is not started..");
		}
	};

	@Override
	public void stop() {
		tf.stop();
	};
}
