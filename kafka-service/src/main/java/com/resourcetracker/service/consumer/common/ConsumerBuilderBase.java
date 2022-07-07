package com.resourcetracker.service.consumer.common;

public class ConsumerBuilderBase<T, V> {
	public ConsumerBuilder<T, V> withOpts(ConsumerBuilderOptions opts) {
		return new ConsumerBuilder<T, V>(opts);
	}
}
