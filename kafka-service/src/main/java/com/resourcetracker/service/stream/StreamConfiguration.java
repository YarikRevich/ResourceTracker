package com.resourcetracker.service.stream;

import java.util.Properties;

import org.apache.kafka.streams.StreamsConfig;

public class StreamConfiguration {
	public static Properties getConfiguration() {
		final Properties streamsConfiguration = new Properties();
		streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "");
		streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
		streamsConfiguration.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
		return streamsConfiguration;
	}
}
