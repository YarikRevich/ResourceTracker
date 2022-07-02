package com.resourcetracker.service.consumer;

import com.resourcetracker.Constants;
import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.common.ConsumerBuilderOptions;
import com.resourcetracker.service.consumer.entity.FiveLastLogsConsumerResult;
import com.resourcetracker.service.entity.LogsEntity;

public class FiveLastLogsConsumer {
	public static ConsumerBuilder<FiveLastLogsConsumerResult, LogsEntity> builder() {
		ConsumerBuilderOptions opts = new ConsumerBuilderOptions();
		opts.setLimit(5);
		opts.setTopic(Constants.KAFKA_LOGS_TOPIC);
		opts.setRecordValueType(Constants.KAFKA_LOGS_TOPIC);

		return new ConsumerBuilder<FiveLastLogsConsumerResult, LogsEntity>()
				.withOpts(opts);
	}
}
