package com.resourcetracker.service.consumer;

import com.resourcetracker.service.consumer.common.ConsumerBase;
import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.entity.StatusSuccessConsumerResult;
import com.resourcetracker.service.entity.StatusEntity;

public class StatusSuccessConsumer extends ConsumerBase<StatusSuccessConsumerResult, StatusEntity> {
	public static ConsumerBuilder<StatusSuccessConsumer> builder() {
		return new ConsumerBuilder<StatusSuccessConsumer>(
				new StatusSuccessConsumer(), new StatusSuccessConsumerOptions().getOpts());
	}
}
