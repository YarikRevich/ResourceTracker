package com.resourcetracker.service.consumer;

import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.entity.StatusSuccessConsumerResult;

public class StatusSuccessConsumer {
	public static ConsumerBuilder builder(){
		return new ConsumerBuilder();
	}

	public StatusSuccessConsumerResult consume() {
		return null;
	};
}
