package com.resourcetracker.service.kafka.producer;

import com.google.common.collect.Maps;
import com.resourcetracker.entity.ExecutionResultEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Properties;

@Service
public class KafkaService {
    private static final Logger logger = LogManager.getLogger(KafkaService.class);

    @Value("${kafka.topic}")
    String topic;

    @Value("${kafka.bootstrap.server}")
    String bootstrapServer;

    private KafkaTemplate<String, ExecutionResultEntity> kafkaTemplate;

    public KafkaService() {
//        Properties properties = new Properties();
//
//        properties.put("bootstrap.servers", bootstrapServer);
//        properties.put("kafkastore.bootstrap.servers", bootstrapServer);
//        properties.put("kafka.topic", topic);
//
//        this.kafkaTemplate = new KafkaTemplate<>(
//                new DefaultKafkaProducerFactory<>(Maps.newHashMap(Maps.fromProperties(properties))));
    }

    public void send(ExecutionResultEntity message) {
        ListenableFuture<SendResult<String, ExecutionResultEntity>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<Object>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.error(ex.getMessage());
            }

            @Override
            public void onSuccess(Object result) {
                logger.info(result);
            }
        });
    }
}
