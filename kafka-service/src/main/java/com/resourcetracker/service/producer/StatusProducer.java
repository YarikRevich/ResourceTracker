package com.resourcetracker.service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.resourcetracker.Constants;
import com.resourcetracker.service.entity.StatusEntity;

@Component
public class StatusProducer {
	KafkaTemplate<String, StatusEntity> kafkaTemplate;

	public void send(StatusEntity data){
		kafkaTemplate.send(Constants.KAFKA_STATUS_TOPIC, data);
	}
}
