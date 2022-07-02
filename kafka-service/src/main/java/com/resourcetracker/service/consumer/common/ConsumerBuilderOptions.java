package com.resourcetracker.service.consumer.common;

public class ConsumerBuilderOptions {
	private int limit;
	private String topic;
	private Object recordValueType;

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

	public void setRecordValueType(Object recordValueType) {
		this.recordValueType = recordValueType;
	}

	public Object getRecordValueType() {
		return this.recordValueType;
	}
}
