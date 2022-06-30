package com.resourcetracker.service.consumer;

import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.entity.FiveLastLogsConsumerResult;

public class FiveLastLogsConsumer {
	public static ConsumerBuilder builder(){
		return new ConsumerBuilder();
	}

	public FiveLastLogsConsumerResult consume() {
		return null;
	};
}
