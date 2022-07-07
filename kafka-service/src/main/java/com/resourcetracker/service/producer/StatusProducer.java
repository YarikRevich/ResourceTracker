package com.resourcetracker.service.producer;

import org.springframework.stereotype.Component;

import com.resourcetracker.service.entity.StatusEntity;
import com.resourcetracker.service.producer.common.ProducerBuilder;
import com.resourcetracker.service.producer.common.ProducerBuilderBase;

public class StatusProducer {
	public static ProducerBuilder<StatusEntity> builder() {
		return new ProducerBuilderBase<StatusEntity>()
				.withOpts(new StatusProducerOptions().getOpts());
	}
}
