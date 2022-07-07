package com.resourcetracker.service.producer.common;

public class ProducerBuilderBase<T> {
	public ProducerBuilder<T> withOpts(ProducerBuilderOptions opts) {
		return new ProducerBuilder<T>(opts);
	}
}
