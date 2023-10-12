package com.resourcetracker.service.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.resourcetracker.service.configuration.common.KafkaConfigurationBuilder;

public class KafkaConfiguration {
	public static KafkaConfigurationBuilder builder(){
		return new KafkaConfigurationBuilder();


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
