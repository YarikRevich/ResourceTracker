package com.resourcetracker.service.consumer.common;

import com.resourcetracker.service.stream.common.Stream;

public class ConsumerBuilder {
	public ConsumerBuilder withStreams(Stream... streams) {
		for (Stream stream : streams) {
			stream.run();
		}
		return this;
	}
}
