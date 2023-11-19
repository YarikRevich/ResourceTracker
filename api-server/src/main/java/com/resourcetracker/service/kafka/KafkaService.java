package com.resourcetracker.service.kafka;

import com.resourcetracker.entity.KafkaLogsTopicEntity;
import com.resourcetracker.service.config.ConfigService;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.Node;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class KafkaService {
    private static final Logger logger = LogManager.getLogger(KafkaService.class);

    @ConfigProperty(name = "kafka.topic")
    String kafkaTopic;

    @Inject
    ConfigService configService;

    private final AdminClient kafkaAdminClient;

    private final KafkaConsumer<String, KafkaLogsTopicEntity> kafkaConsumer;

    public KafkaService() {
        Properties kafkaAdminClientProps = new Properties();

        String kafkaBootstrapServer = configService.getConfig().getKafka().getHost();

        kafkaAdminClientProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
        kafkaAdminClientProps.put(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000);
        kafkaAdminClientProps.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 5000);

        this.kafkaAdminClient = AdminClient.create(kafkaAdminClientProps);

        Properties kafkaConsumerProps = new Properties();

        kafkaConsumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer);
        kafkaConsumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonDeserializer");

        this.kafkaConsumer = new KafkaConsumer<>(kafkaConsumerProps);
        this.kafkaConsumer.subscribe(Collections.singletonList(kafkaTopic));
    }

    public boolean isConnected() {
        Collection<Node> nodes = null;
        try {
            nodes = kafkaAdminClient.describeCluster()
                    .nodes()
                    .get();
        } catch (ExecutionException | InterruptedException e) {
            logger.fatal(e.getMessage());
        }

        return nodes != null && !nodes.isEmpty();
    }

    public List<KafkaLogsTopicEntity> consumeLogs() {
        List<KafkaLogsTopicEntity> kafkaLogsTopicEntities = new ArrayList<>();

        ConsumerRecords<String, KafkaLogsTopicEntity> records = kafkaConsumer.poll(Duration.ofSeconds(5));

        ListIterator<ConsumerRecord<String, KafkaLogsTopicEntity>> iter = (ListIterator<ConsumerRecord<String, KafkaLogsTopicEntity>>) records.iterator();

        while (iter.hasNext()) {
            ConsumerRecord<String, KafkaLogsTopicEntity> record = iter.next();
            kafkaLogsTopicEntities.add(record.value());
        }

        return kafkaLogsTopicEntities;
    }

    @PreDestroy
    private void close() {
        kafkaAdminClient.close();
        kafkaConsumer.close();
    }
}
