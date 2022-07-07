package com.resourcetracker.service.consumer;

import com.resourcetracker.Constants;
import com.resourcetracker.service.consumer.common.ConsumerBuilderOptions;

public class FiveLastLogsConsumerOptions {
	private ConsumerBuilderOptions opts = new ConsumerBuilderOptions();

	public FiveLastLogsConsumerOptions(){
		opts.setLimit(5);
		opts.setTopic(Constants.KAFKA_LOGS_TOPIC);
		// opts.setRecordValueType(Constants.KAFKA_LOGS_TOPIC);
	}

	public ConsumerBuilderOptions getOpts(){
		return opts;
	}
}
