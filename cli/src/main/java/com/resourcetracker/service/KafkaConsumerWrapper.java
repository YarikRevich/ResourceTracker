package com.resourcetracker.service;

import io.reactivex.rxjava3.core.Observable;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Properties;

@Service
public class KafkaConsumerWrapper {
	private KafkaConsumer consumer;

	public KafkaConsumerWrapper(){
		Properties config = new Properties();
		try {
			config.put(AdminClientConfig.CLIENT_ID_CONFIG, InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e){
			e.printStackTrace();
		}
		config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "");
		this.consumer = new KafkaConsumer<>(config);
	}

	public String receive(String topic){
		ConsumerRecords<?, ?> records = consumer.poll(Duration.ofSeconds(10));
		records.forEach((ConsumerRecord<?,?> e) -> {
			if (e.topic() == topic){
				System.out.println(e.value().toString());
			}
		});
		consumer.commitAsync();
		return "";
	};
}
