package com.resourcetracker.service.producer;

import org.springframework.stereotype.Component;

import com.resourcetracker.service.entity.LogsEntity;
import com.resourcetracker.service.producer.common.ProducerBase;
import com.resourcetracker.service.producer.common.ProducerBuilder;

public class LogsProducer extends ProducerBase<LogsEntity> {
	public static ProducerBuilder<LogsProducer> builder() {
		return new ProducerBuilder<LogsProducer>(
				new LogsProducer(), new LogsProducerOptions().getOpts());
	}
}
