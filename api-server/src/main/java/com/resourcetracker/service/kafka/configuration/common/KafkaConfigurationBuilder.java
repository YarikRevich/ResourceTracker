package com.resourcetracker.service.kafka.configuration.common;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.streams.StreamsConfig;
//import org.springframework.beans.factory.annotation.Value;

public class KafkaConfigurationBuilder {
//  @Value("#{environment.RESOURCETRACKER_KAFKA_BOOTSTRAP_SERVER}")
  private String envBootstrapServer;
  public String bootstrapServer;
  public String clientId;
  private String applicationId;
  private String groupId;

  public KafkaConfigurationBuilder withBootstrapServer(String bootstrapServer) {
    this.bootstrapServer = bootstrapServer;
    return this;
  }

  public KafkaConfigurationBuilder withClientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  public KafkaConfigurationBuilder withApplicationId(String applicationId) {
    this.applicationId = applicationId;
    return this;
  }

  public KafkaConfigurationBuilder withGroupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

  /**
   * Loads configuration for Kafka from 'application.yml'
   * file depending on the module 'kafka-service' is used in
   *
   * @return Loaded properties file
   */
  public Properties build() {
    final Properties streamsConfiguration = new Properties();
    if (envBootstrapServer != null) {
      streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, envBootstrapServer);
    } else if (bootstrapServer != null) {
      streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    } else {
      throw new RuntimeException("no 'bootstrap-servers' set in 'KafkaConfiguration'");
    }

    if (clientId != null) {
      streamsConfiguration.put(StreamsConfig.CLIENT_ID_CONFIG, clientId);
    } else {
      throw new RuntimeException("no 'clientId' set in 'KafkaConfiguration'");
    }

    if (applicationId != null) {
      streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
    } else {
      throw new RuntimeException("no 'applicationId' set in 'KafkaConfiguration'");
    }

    if (groupId != null) {
      streamsConfiguration.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    } else {
      throw new RuntimeException("no 'groupId' set in 'KafkaConfiguration'");
    }

    streamsConfiguration.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
    streamsConfiguration.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonSerializer");

    streamsConfiguration.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
    streamsConfiguration.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonDeserializer");

    // spring.kafka.producer.key-serializer: org.apache.kafka.common.serialization.StringSerializer
// spring.kafka.producer.value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
    // streamsConfiguration.setProperty("key.deserializer",
    // 		"org.springframework.kafka.support.serializer.JsonDeserializer");
    // streamsConfiguration.setProperty("value.deserializer",
    // 		"org.springframework.kafka.support.serializer.JsonDeserializer");
    // streamsConfiguration.setProperty("key.serializer",
    // 		"org.springframework.kafka.support.serializer.JsonSerializer");
    // streamsConfiguration.setProperty("value.serializer",
    // 		"org.springframework.kafka.support.serializer.JsonSerializer");
    // streamsConfiguration.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 10 * 1000);
    // streamsConfiguration.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
    return streamsConfiguration;
  }
}
