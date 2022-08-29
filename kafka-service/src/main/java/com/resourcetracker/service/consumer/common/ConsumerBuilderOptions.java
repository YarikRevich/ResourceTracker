package com.resourcetracker.service.consumer.common;

public class ConsumerBuilderOptions {
	private int limit = Integer.MAX_VALUE;
	private String topic;

	public int getLimit() {
		return this.limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
}
