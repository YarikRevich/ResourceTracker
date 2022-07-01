package com.resourcetracker.service.consumer;

import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.entity.StatusFailureConsumerResult;

public class StatusFailureConsumer {
	public static ConsumerBuilder<StatusFailureConsumerResult> builder(){
		return new ConsumerBuilder<StatusFailureConsumerResult>();
	}
}
