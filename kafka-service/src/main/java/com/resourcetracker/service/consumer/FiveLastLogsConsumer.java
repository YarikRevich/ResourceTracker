package com.resourcetracker.service.consumer;

import com.resourcetracker.service.consumer.common.ConsumerBase;
import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.entity.FiveLastLogsConsumerResult;
import com.resourcetracker.service.entity.LogsEntity;

public class FiveLastLogsConsumer extends ConsumerBase<FiveLastLogsConsumerResult, LogsEntity> {
	public static ConsumerBuilder<FiveLastLogsConsumer> builder() {
		return new ConsumerBuilder<FiveLastLogsConsumer>(
				new FiveLastLogsConsumer(), new FiveLastLogsConsumerOptions().getOpts());
	}
}
