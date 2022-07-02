package com.resourcetracker.service.configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class KafkaConfiguration {

	/**
	 * Loads configuration for Kafka from 'application.yml'
	 * file depending on the module 'kafka-service' is used in
	 *
	 * @return Loaded properties file
	 */
	public static Properties getConfiguration() {
		final Properties streamsConfiguration = new Properties();
		try {
			streamsConfiguration.load(new FileReader("resources/application.yaml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
		// streamsConfiguration.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
		return streamsConfiguration;
	}
}
