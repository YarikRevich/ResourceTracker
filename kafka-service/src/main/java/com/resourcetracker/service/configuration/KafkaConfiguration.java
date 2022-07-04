package com.resourcetracker.service.configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;

import com.resourcetracker.service.configuration.common.KafkaConfigurationBuilder;

public class KafkaConfiguration {
	public static KafkaConfigurationBuilder builder(){
		return new KafkaConfigurationBuilder();
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



	// if (stateService.isKafkaBootstrapServer()){
	// 	properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, stateService.getKafkaBootstrapServer());
	// 	this.consumer = new KafkaConsumer<>(properties);
	// }
}
