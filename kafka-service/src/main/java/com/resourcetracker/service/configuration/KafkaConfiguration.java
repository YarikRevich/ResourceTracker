package com.resourcetracker.service.configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;

public class KafkaConfiguration {

	@Value("#{environment.RESOURCETRACKER_KAFKA_BOOTSTRAP_SERVER}")
	private static String envBoostrapServer;

	public static String bootrapServer;

	public static String getBoostrapServer() {
		return bootrapServer;
	}

	public static void setBoostrapServer(String bootrapServer) {
		KafkaConfiguration.bootrapServer = bootrapServer;
	}
	/**
	 * Loads configuration for Kafka from 'application.yml'
	 * file depending on the module 'kafka-service' is used in
	 *
	 * @return Loaded properties file
	 */
	public static Properties getConfiguration() {
		final Properties streamsConfiguration = new Properties();
		if (envBoostrapServer != null){
			streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, envBoostrapServer);
		} else if (bootrapServer != null){
			streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootrapServer);
		} else {
			throw new RuntimeException("no 'bootstrap-servers' set in 'KafkaConfiguration'");
		}
		// streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
		// streamsConfiguration.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
		return streamsConfiguration;
	}

	public static Map<String, Object> convertPropsToMap(Properties props){
		Map<String, Object> retMap = new HashMap<>();
		for (Map.Entry<Object, Object> prop : props.entrySet()) {
			retMap.put(String.valueOf(prop.getKey()), String.valueOf(prop.getValue()));
		}
		return retMap;
	}


	// try {
	// 	properties.put(AdminClientConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostName());
	// } catch (UnknownHostException e){
	// 	e.printStackTrace();
	// }

	// properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	// properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

	// if (stateService.isKafkaBootstrapServer()){
	// 	properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, stateService.getKafkaBootstrapServer());
	// 	this.consumer = new KafkaConsumer<>(properties);
	// }
}
