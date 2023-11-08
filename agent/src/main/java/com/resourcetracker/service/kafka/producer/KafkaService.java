package com.resourcetracker.service.kafka.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Properties;

@Service
public class KafkaService {
    @Value("${kafka.topic}")
    String topic;

    @Value("${kafka.bootstrap.server}")
    String bootstrapServer;

    private KafkaTemplate<String, T> kafkaTemplate;

    public KafkaService() {
        Properties properties = new Properties();

        properties.put("bootstrap.servers", bootstrapServer);
        properties.put("kafkastore.bootstrap.servers", bootstrapServer);
        properties.put("kafka.topic", topic);

        kafkaTemplate = new KafkaTemplate<>(
          new DefaultKafkaProducerFactory<String, T>(
              KafkaConfiguration.convertPropsToMap(properties)));
    }

    public void send() {
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
}
