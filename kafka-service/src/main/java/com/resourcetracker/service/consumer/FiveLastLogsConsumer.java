package com.resourcetracker.service.consumer;

import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.common.ConsumerBuilderBase;
import com.resourcetracker.service.consumer.entity.FiveLastLogsConsumerResult;
import com.resourcetracker.service.entity.LogsEntity;

public class FiveLastLogsConsumer {
	public static ConsumerBuilder<FiveLastLogsConsumerResult, LogsEntity> builder() {
		return new ConsumerBuilderBase<FiveLastLogsConsumerResult, LogsEntity>()
				.withOpts(new FiveLastLogsConsumerOptions().getOpts());
	}
}
