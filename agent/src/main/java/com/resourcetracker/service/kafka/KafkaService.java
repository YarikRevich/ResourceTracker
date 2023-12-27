package com.resourcetracker.service.kafka;

import com.resourcetracker.entity.KafkaLogsTopicEntity;
import com.resourcetracker.entity.PropertiesEntity;
import jakarta.annotation.PreDestroy;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** KafkaService provides access to Kafka cluster. */
@Component
public class KafkaService {
  private static final Logger logger = LogManager.getLogger(KafkaService.class);

  private final PropertiesEntity properties;

  private final KafkaProducer<String, KafkaLogsTopicEntity> kafkaProducer;

  public KafkaService(@Autowired PropertiesEntity properties) {
    Properties kafkaProducerProps = new Properties();

    kafkaProducerProps.put(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getKafkaBootstrapServer());
    kafkaProducerProps.put(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringSerializer");
    kafkaProducerProps.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        "org.springframework.kafka.support.serializer.JsonSerializer");

    this.kafkaProducer = new KafkaProducer<>(kafkaProducerProps);
    this.properties = properties;
  }

  /**
   * Sends message to the Kafka cluster, which credentials are specified as environment variables.
   *
   * @param message message to be sent to Kafka cluster via Kafka producer
   */
  public void send(KafkaLogsTopicEntity message) {
    System.out.println(message.toString());
    //
    //    Future<RecordMetadata> future =
    //        kafkaProducer.send(new ProducerRecord<>(properties.getKafkaTopic(), message));
    //
    //    if (!future.isDone() || future.isCancelled()) {
    //      logger.error(new KafkaProducerSendException().getMessage());
    //    }
  }

  /** Closes a connection of Kafka producer after the application is closed. */
  @PreDestroy
  private void close() {
    kafkaProducer.close();
  }
}
