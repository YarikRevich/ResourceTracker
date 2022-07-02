package com.resourcetracker.service.consumer;

import com.resourcetracker.Constants;
import com.resourcetracker.service.consumer.common.ConsumerBuilder;
import com.resourcetracker.service.consumer.common.ConsumerBuilderOptions;
import com.resourcetracker.service.consumer.entity.StatusSuccessConsumerResult;
import com.resourcetracker.service.entity.StatusEntity;

public class StatusSuccessConsumer {
	public static ConsumerBuilder<StatusSuccessConsumerResult, StatusEntity> builder() {
		ConsumerBuilderOptions opts = new ConsumerBuilderOptions();
		opts.setTopic(Constants.KAFKA_STATUS_SUCCESS_TOPIC);
		opts.setRecordValueType(null);

		return new ConsumerBuilder<StatusSuccessConsumerResult, StatusEntity>()
				.withOpts(opts);
	}
}
