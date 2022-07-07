package com.resourcetracker.service.producer.common;

import java.util.Properties;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.resourcetracker.service.configuration.KafkaConfiguration;

public class ProducerBase<T> {

}
