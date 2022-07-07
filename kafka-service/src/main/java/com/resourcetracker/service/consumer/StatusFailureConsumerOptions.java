package com.resourcetracker.service.consumer;

import com.resourcetracker.Constants;
import com.resourcetracker.service.consumer.common.ConsumerBuilderOptions;

public class StatusFailureConsumerOptions {
	private ConsumerBuilderOptions opts = new ConsumerBuilderOptions();

	public StatusFailureConsumerOptions() {
		opts.setTopic(Constants.KAFKA_STATUS_FAILURE_TOPIC);
		// opts.setRecordValueType(null);
	}

	public ConsumerBuilderOptions getOpts() {
		return opts;
	}
}
