package com.resourcetracker.service.producer;

import org.springframework.stereotype.Component;

import com.resourcetracker.Constants;
import com.resourcetracker.service.entity.StatusEntity;
import com.resourcetracker.service.producer.common.ProducerBase;

@Component
public class StatusProducer extends ProducerBase<StatusEntity> {
	public StatusProducer() {
		super(Constants.KAFKA_STATUS_TOPIC);
	}
}
