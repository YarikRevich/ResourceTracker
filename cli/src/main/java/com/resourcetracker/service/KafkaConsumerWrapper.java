package com.resourcetracker.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Properties;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import com.resourcetracker.StateService;

@Service
public class KafkaConsumerWrapper {
	private Properties properties;
	private KafkaConsumer<String, String> consumer;

	StateService stateService = null;

	public KafkaConsumerWrapper(){
		stateService = new StateService();

		properties = new Properties();
		try {
			properties.put(AdminClientConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e){
			e.printStackTrace();
		}

		properties.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		if (stateService.isKafkaBootstrapServer()){
			properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, stateService.getKafkaBootstrapServer());
			this.consumer = new KafkaConsumer<>(properties);
		}
	}

	private String receive(String topic, String project){
		ConsumerRecords<?, ?> records = consumer.poll(Duration.ofSeconds(10));
		records.forEach((ConsumerRecord<?,?> e) -> {
			if (e.topic() == topic){
				System.out.println(e.value().toString());
			}
		});


		consumer.commitAsync();
		return "";
	};

	public String receiveStatus(String project){
		return this.receive("status", project);
	}
}
