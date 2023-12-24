package com.resourcetracker.service.kafka;

import com.resourcetracker.entity.KafkaLogsTopicEntity;
import com.resourcetracker.service.config.ConfigService;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class KafkaService {
  private static final Logger logger = LogManager.getLogger(KafkaService.class);

  private AdminClient kafkaAdminClient;

  private final String kafkaBootstrapServer;

  private final Properties kafkaAdminClientProps;

  private final KafkaConsumer<String, KafkaLogsTopicEntity> kafkaConsumer;

  @Inject
  public KafkaService(
      ConfigService configService, @ConfigProperty(name = "kafka.topic") String kafkaTopic) {
    Properties kafkaAdminClientProps = new Properties();

    this.kafkaBootstrapServer = configService.getConfig().getKafka().getHost();

    kafkaAdminClientProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
    kafkaAdminClientProps.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5000);
    kafkaAdminClientProps.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 5000);
    kafkaAdminClientProps.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 5000);

    this.kafkaAdminClientProps = kafkaAdminClientProps;

    Properties kafkaConsumerProps = new Properties();

    kafkaConsumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
    kafkaConsumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
    kafkaConsumerProps.put(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        "org.apache.kafka.common.serialization.StringDeserializer");
    kafkaConsumerProps.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        "org.springframework.kafka.support.serializer.JsonDeserializer");

    this.kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProps);
    this.kafkaConsumer.subscribe(Collections.singletonList(kafkaTopic));
  }

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

  private boolean isAvailable() {
    URL url;
    try {
      url = URI.create(kafkaBootstrapServer).toURL();
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

  public List<KafkaLogsTopicEntity> consumeLogs() {
    List<KafkaLogsTopicEntity> kafkaLogsTopicEntities = new ArrayList<>();

    ConsumerRecords<String, KafkaLogsTopicEntity> records =
        kafkaConsumer.poll(Duration.ofSeconds(5));

    ListIterator<ConsumerRecord<String, KafkaLogsTopicEntity>> iter =
        (ListIterator<ConsumerRecord<String, KafkaLogsTopicEntity>>) records.iterator();

    while (iter.hasNext()) {
      ConsumerRecord<String, KafkaLogsTopicEntity> record = iter.next();
      kafkaLogsTopicEntities.add(record.value());
    }

    return kafkaLogsTopicEntities;
  }

  @PreDestroy
  private void close() {
    if (!Objects.isNull(kafkaAdminClient)) {
      kafkaAdminClient.close();
    }

    kafkaConsumer.close();
  }
}
