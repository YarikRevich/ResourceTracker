package com.resourcetracker.service.consumer;

import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.common.ConsumerBuilderBase;
import com.resourcetracker.service.consumer.entity.StatusFailureConsumerResult;
import com.resourcetracker.service.entity.StatusEntity;

public class StatusFailureConsumer {
	public static ConsumerBuilder<StatusFailureConsumerResult, StatusEntity> builder() {
		return new ConsumerBuilderBase<StatusFailureConsumerResult, StatusEntity>()
				.withOpts(new StatusFailureConsumerOptions().getOpts());
	}
}
