package com.resourcetracker.service.consumer;

import com.resourcetracker.Constants;
import com.resourcetracker.service.consumer.common.ConsumerBuilderOptions;

public class StatusSuccessConsumerOptions {
	private ConsumerBuilderOptions opts = new ConsumerBuilderOptions();

	public StatusSuccessConsumerOptions(){
		opts.setTopic(Constants.KAFKA_STATUS_SUCCESS_TOPIC);
	}

	public ConsumerBuilderOptions getOpts(){
		return opts;
	}
}
