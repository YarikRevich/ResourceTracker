package com.resourcetracker.service.producer.common;

import java.util.Properties;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.resourcetracker.service.configuration.KafkaConfiguration;

public class ProducerBase<T> implements ProducerBuilderSource {
	private ProducerBuilderResult producerBuilderResult;

	private KafkaTemplate<String, T> kafkaTemplate;

	public void setProducerBuilderResult(ProducerBuilderResult producerBuilderResult) {
		this.producerBuilderResult = producerBuilderResult;
	}

	public void send(T data, Runnable onSuccess, Runnable onFailure) {
		if (kafkaTemplate == null) {
			kafkaTemplate = new KafkaTemplate<>(
					new DefaultKafkaProducerFactory<String, T>(
							KafkaConfiguration.convertPropsToMap(producerBuilderResult.getProps())));
		}

		ListenableFuture<SendResult<String, T>> future = this.kafkaTemplate.send(this.producerBuilderResult.getTopic(),
				data);
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

	public void send(T data) {
		this.send(data, null, null);
	}
}
