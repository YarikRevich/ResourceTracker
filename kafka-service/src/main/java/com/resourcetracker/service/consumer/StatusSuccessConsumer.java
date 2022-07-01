package com.resourcetracker.service.consumer;

import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.entity.StatusSuccessConsumerResult;

public class StatusSuccessConsumer {
	public static ConsumerBuilder<StatusSuccessConsumerResult> builder(){
		return new ConsumerBuilder<StatusSuccessConsumerResult>();
	}
}
