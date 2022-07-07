package com.resourcetracker.service.producer.common;

import java.util.Properties;

public class ProducerBuilderResult {
	private Properties props;
	private String topic;
	private ProducerBuilderOptions opts;

	public ProducerBuilderOptions getOpts() {
		return this.opts;
	}

	public void setOpts(ProducerBuilderOptions opts) {
		this.opts = opts;
	}

	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Properties getProps() {
		return this.props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
}
