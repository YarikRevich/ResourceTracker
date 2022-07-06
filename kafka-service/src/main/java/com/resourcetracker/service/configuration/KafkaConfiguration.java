package com.resourcetracker.service.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

	public static Properties convertMapToProps(Map<String, Object> map){
		Properties props = new Properties();
		for (Map.Entry<String, Object> prop : map.entrySet()) {
			props.put(String.valueOf(prop.getKey()), prop.getValue());
		}
		return props;
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
