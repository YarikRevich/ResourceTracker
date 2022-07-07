package com.resourcetracker.service.producer;

import org.springframework.stereotype.Component;

import com.resourcetracker.service.entity.LogsEntity;
import com.resourcetracker.service.producer.common.ProducerBuilder;
import com.resourcetracker.service.producer.common.ProducerBuilderBase;

public class LogsProducer {
	public static ProducerBuilder<LogsEntity> builder() {
		return new ProducerBuilderBase<LogsEntity>()
				.withOpts(new LogsProducerOptions().getOpts());
	}
}
