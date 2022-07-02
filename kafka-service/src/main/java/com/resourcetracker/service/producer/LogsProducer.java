package com.resourcetracker.service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.resourcetracker.Constants;
import com.resourcetracker.service.entity.LogsEntity;

@Component
public class LogsProducer {
	KafkaTemplate<String, LogsEntity> kafkaTemplate;

	public void send(LogsEntity data){
		kafkaTemplate.send(Constants.KAFKA_LOGS_TOPIC, data);
	}
}

