package com.resourcetracker.service.consumer;

import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.common.ConsumerBuilderBase;
import com.resourcetracker.service.consumer.entity.StatusSuccessConsumerResult;
import com.resourcetracker.service.entity.StatusEntity;

public class StatusSuccessConsumer {
	public static ConsumerBuilder<StatusSuccessConsumerResult, StatusEntity> builder() {
		return new ConsumerBuilderBase<StatusSuccessConsumerResult, StatusEntity>()
				.withOpts(new StatusSuccessConsumerOptions().getOpts());
	}
}
