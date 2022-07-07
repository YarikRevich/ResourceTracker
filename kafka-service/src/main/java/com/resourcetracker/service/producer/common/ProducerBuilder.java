package com.resourcetracker.service.producer.common;

import java.util.Properties;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.resourcetracker.service.configuration.KafkaConfiguration;

public class ProducerBuilder<T> {
	private ProducerBuilderOptions opts;
	private KafkaTemplate<String, T> kafkaTemplate;


	public ProducerBuilder(ProducerBuilderOptions opts){
		this.opts = opts;
	}

	public ProducerBuilder<T> withProps(Properties props){
		kafkaTemplate = new KafkaTemplate<>(
				new DefaultKafkaProducerFactory<String, T>(KafkaConfiguration.convertPropsToMap(props)));
		return this;
	}

	public void send(T data, Runnable onSuccess, Runnable onFailure) {
		ListenableFuture<SendResult<String, T>> future = this.kafkaTemplate.send(this.opts.getTopic(), data);
		future.addCallback(new ListenableFutureCallback<Object>() {
			@Override
			public void onFailure(Throwable ex) {
				if (onFailure != null) {
					onFailure.run();
				}
			}

			@Override
			public void onSuccess(Object result) {
				if (onSuccess != null) {
					onSuccess.run();
				}
			}
		});
	}

	public void send(T data){
		this.send(data, null, null);
	}
}
