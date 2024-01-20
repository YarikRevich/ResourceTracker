package com.resourcetracker.service.kafka;

import com.resourcetracker.dto.KafkaLogsTopicDto;
import com.resourcetracker.entity.PropertiesEntity;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.net.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.errors.UnsupportedVersionException;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/** */
public class KafkaService {
  private AdminClient kafkaAdminClient;

  private final String kafkaBootstrapServer;

  private final Properties kafkaAdminClientProps;

  private final KafkaConsumer<String, KafkaLogsTopicDto> kafkaConsumer;

  public KafkaService(String kafkaBootstrapServerHost, PropertiesEntity properties) {
    String kafkaBootstrapServer =
        String.format(
            "%s:%d", kafkaBootstrapServerHost, properties.getResourceTrackerKafkaMainPort());

    Properties kafkaAdminClientProps = new Properties();

    kafkaAdminClientProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
    kafkaAdminClientProps.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
    kafkaAdminClientProps.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 5000);
    kafkaAdminClientProps.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 5000);

    this.kafkaAdminClientProps = kafkaAdminClientProps;

    Properties kafkaConsumerProps = new Properties();

    String groupId = UUID.randomUUID().toString();

    kafkaConsumerProps.put(
        ConsumerConfig.GROUP_INSTANCE_ID_CONFIG,
        String.format("scheduler_coordinator_%s", groupId));
    kafkaConsumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    kafkaConsumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    kafkaConsumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    kafkaConsumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    kafkaConsumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
    kafkaConsumerProps.put(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringDeserializer");
    kafkaConsumerProps.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        "org.springframework.kafka.support.serializer.JsonDeserializer");

    this.kafkaBootstrapServer = kafkaBootstrapServer;

    this.kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProps);
    this.kafkaConsumer.subscribe(Collections.singletonList(properties.getKafkaTopic()));
  }

  /**
   * Checks if Kafka service is connected to Kafka cluster at the given address.
   *
   * @return result of the check.
   */
  public boolean isConnected() {
    if (isAvailable()) {
      if (Objects.isNull(kafkaAdminClient)) {
        this.kafkaAdminClient = AdminClient.create(kafkaAdminClientProps);
      }

      Collection<Node> nodes;
      try {
        nodes = kafkaAdminClient.describeCluster().nodes().get();
      } catch (ExecutionException
          | UnsupportedVersionException
          | TimeoutException
          | InterruptedException e) {
        return false;
      }

      return nodes != null && !nodes.isEmpty();
    }

    return false;
  }

  /**
   * Checks if Kafka service is available at the given address.
   *
   * @return result of the check.
   */
  public boolean isAvailable() {
    URL url;

    try {
      url = URI.create(String.format("http://%s", kafkaBootstrapServer)).toURL();
    } catch (MalformedURLException e) {
      return false;
    }

    try (Socket ignored = new Socket(url.getHost(), url.getPort())) {
      ignored.close();

      return true;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Retrieves messages from "logs" topic.
   *
   * @return retrieved messages.
   */
  public List<KafkaLogsTopicDto> consumeLogs() {
    List<KafkaLogsTopicDto> result = new ArrayList<>();

    ConsumerRecords<String, KafkaLogsTopicDto> records = kafkaConsumer.poll(Duration.ofSeconds(30));

    for (ConsumerRecord<String, KafkaLogsTopicDto> record : records) {
      result.add(record.value());
    }

    return result;
  }

  @PreDestroy
  private void close() {
    if (!Objects.isNull(kafkaAdminClient)) {
      kafkaAdminClient.close();
    }

    kafkaConsumer.close();
  }
}
