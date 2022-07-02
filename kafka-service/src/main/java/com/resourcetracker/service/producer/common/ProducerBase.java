package com.resourcetracker.service.producer.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.resourcetracker.service.configuration.KafkaConfiguration;

public class ProducerBase<T> {
	public KafkaTemplate<String, T> kafkaTemplate = null;

	public void init(Properties props){
		kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<String, T>(KafkaConfiguration.convertPropsToMap(props)));
	}
}
