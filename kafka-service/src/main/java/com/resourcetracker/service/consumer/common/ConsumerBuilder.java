package com.resourcetracker.service.consumer.common;

import com.resourcetracker.service.stream.common.Stream;

public class ConsumerBuilder<T> {
	public ConsumerBuilder<T> withStreams(Stream... streams) {
		for (Stream stream : streams) {
			stream.run();
		}
		return this;
	}

	public T consume() {
		return null;
	};
}
