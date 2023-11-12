package com.resourcetracker.service.kafka;

import com.resourcetracker.entity.KafkaLogsTopicEntity;
import com.resourcetracker.exception.KafkaProducerSendException;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.concurrent.Future;

@Service
public class KafkaService {
    private static final Logger logger = LogManager.getLogger(KafkaService.class);

    @Value("${kafka.topic}")
    String kafkaTopic;

    private final KafkaProducer<String, KafkaLogsTopicEntity> kafkaProducer;

    public KafkaService(@Value("${kafka.bootstrap.server}") String kafkaBootstrapServer) {
        Properties kafkaProducerProperties = new Properties();

        kafkaProducerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);

        this.kafkaProducer = new KafkaProducer<>(kafkaProducerProperties);
    }

    public void send(KafkaLogsTopicEntity message) {
        Future<RecordMetadata> future = kafkaProducer
                .send(new ProducerRecord<>(kafkaTopic, message));

        if (!future.isDone() || future.isCancelled()){
            logger.fatal(new KafkaProducerSendException().getMessage());
        }
    }

    @PreDestroy
    private void close(){
        kafkaProducer.close();
    }
}
