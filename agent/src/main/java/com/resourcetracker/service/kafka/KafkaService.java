package com.resourcetracker.service.kafka;

import com.resourcetracker.entity.KafkaLogsTopicEntity;
import com.resourcetracker.exception.InvalidBootstrapServerEnvironmentVariableException;
import com.resourcetracker.exception.KafkaProducerSendException;
import jakarta.annotation.PreDestroy;
import java.util.Properties;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** KafkaService provides access to Kafka cluster. */
@Service
public class KafkaService {
  private static final Logger logger = LogManager.getLogger(KafkaService.class);

  @Value("${kafka.topic}")
  String kafkaTopic;

  private final KafkaProducer<String, KafkaLogsTopicEntity> kafkaProducer;

  /**
   * Default constructor, which using Kafka credentials given as environment variables establishes
   * connection with Kafka producer interface
   *
   * @param kafkaBootstrapServer address of Kafka cluster
   */
  public KafkaService(
      @Value("${RESOURCETRACKER_KAFKA_BOOTSTRAP_SERVER:null}") String kafkaBootstrapServer)
      throws InvalidBootstrapServerEnvironmentVariableException {
    if (kafkaBootstrapServer.equals("null")) {
      throw new InvalidBootstrapServerEnvironmentVariableException();
    }

    Properties kafkaProducerProps = new Properties();

    kafkaProducerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
    kafkaProducerProps.put(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringSerializer");
    kafkaProducerProps.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        "org.springframework.kafka.support.serializer.JsonSerializer");

    this.kafkaProducer = new KafkaProducer<>(kafkaProducerProps);
  }

  /**
   * Sends message to the Kafka cluster, which credentials are specified as environment variables.
   *
   * @param message message to be sent to Kafka cluster via Kafka producer
   */
  public void send(KafkaLogsTopicEntity message) {
    Future<RecordMetadata> future = kafkaProducer.send(new ProducerRecord<>(kafkaTopic, message));

    if (!future.isDone() || future.isCancelled()) {
      logger.fatal(new KafkaProducerSendException().getMessage());
    }
  }

  /** Closes a connection of Kafka producer after the application is closed. */
  @PreDestroy
  private void close() {
    kafkaProducer.close();
  }
}
