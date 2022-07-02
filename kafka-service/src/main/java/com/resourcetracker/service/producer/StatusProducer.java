package com.resourcetracker.service.producer;

import java.util.Properties;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.resourcetracker.Constants;
import com.resourcetracker.service.entity.StatusEntity;
import com.resourcetracker.service.producer.common.ProducerBase;

@Component
public class StatusProducer extends ProducerBase<StatusEntity>{
	public void send(StatusEntity data){
		this.kafkaTemplate.send(Constants.KAFKA_STATUS_TOPIC, data);
	}
}
