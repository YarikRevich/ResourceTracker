package com.resourcetracker.service.consumer;

import com.resourcetracker.Constants;
import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.common.ConsumerBuilderOptions;
import com.resourcetracker.service.consumer.entity.StatusFailureConsumerResult;
import com.resourcetracker.service.entity.StatusEntity;

public class StatusFailureConsumer {
	public static ConsumerBuilder<StatusFailureConsumerResult, StatusEntity> builder() {
		ConsumerBuilderOptions opts = new ConsumerBuilderOptions();
		opts.setTopic(Constants.KAFKA_STATUS_FAILURE_TOPIC);
		opts.setRecordValueType(null);

		return new ConsumerBuilder<StatusFailureConsumerResult, StatusEntity>()
				.withOpts(opts);
	}
}
