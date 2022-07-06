package com.resourcetracker.service.producer;

import org.springframework.stereotype.Component;

import com.resourcetracker.Constants;
import com.resourcetracker.service.entity.LogsEntity;
import com.resourcetracker.service.producer.common.ProducerBase;

@Component
public class LogsProducer extends ProducerBase<LogsEntity>{
	public LogsProducer() {
		super(Constants.KAFKA_LOGS_TOPIC);
	}
}

