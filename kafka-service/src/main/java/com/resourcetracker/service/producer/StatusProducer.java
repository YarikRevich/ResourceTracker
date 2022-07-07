package com.resourcetracker.service.producer;

import org.springframework.stereotype.Component;

import com.resourcetracker.service.entity.StatusEntity;
import com.resourcetracker.service.producer.common.ProducerBase;
import com.resourcetracker.service.producer.common.ProducerBuilder;

public class StatusProducer extends ProducerBase<StatusEntity>{
	public static ProducerBuilder<StatusProducer> builder() {
		return new ProducerBuilder<StatusProducer>(
				new StatusProducer(), new StatusProducerOptions().getOpts());
	}
}
