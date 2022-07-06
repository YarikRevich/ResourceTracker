package com.resourcetracker.service.producer.common;

import java.util.Properties;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.resourcetracker.service.configuration.KafkaConfiguration;

public class ProducerBase<T> {
	public KafkaTemplate<String, T> kafkaTemplate = null;

	private String topic;

	public ProducerBase(String topic) {
		this.topic = topic;
	}

	public void init(Properties props) {
		ClassLoader original = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(null);
		kafkaTemplate = new KafkaTemplate<>(
				new DefaultKafkaProducerFactory<String, T>(KafkaConfiguration.convertPropsToMap(props)));
		Thread.currentThread().setContextClassLoader(original);
	}

	public void send(T data, Runnable onSuccess, Runnable onFailure) {
		ListenableFuture<SendResult<String, T>> future = this.kafkaTemplate.send(topic, data);
		future.addCallback(new ListenableFutureCallback<Object>() {
			@Override
			public void onFailure(Throwable ex) {
				onFailure.run();
			}

			@Override
			public void onSuccess(Object result) {
				onSuccess.run();
			}
		});
	}
}
