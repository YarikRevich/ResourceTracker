package com.resourcetracker.kafka;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Import;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.testcontainers.containers.KafkaContainer;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.DockerImageName;

//import com.resourcetracker.service.configuration.KafkaConfiguration;
//import com.resourcetracker.service.consumer.StatusFailureConsumer;
//import com.resourcetracker.service.consumer.entity.StatusFailureConsumerResult;
//import com.resourcetracker.service.entity.StatusEntity;
//import com.resourcetracker.service.entity.StatusEntity.StatusType;
//import com.resourcetracker.service.producer.StatusProducer;
//import com.resourcetracker.service.stream.StatusSplitStream;

/**
 * Integrational tests for StatusSplitStream
 */
//@Testcontainers
//@DirtiesContext
//@Import({ StatusProducer.class })
//@ExtendWith(SpringExtension.class)
public class KafkaTest {
//    private static final Logger logger = LogManager.getLogger(StatusSplitStream.class);

    private Properties kafkaConfiguration;

//    private KafkaContainer kafka;

//    @BeforeEach
    public void setUp() {
//        kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));
//        kafka.start();
//
//        kafkaConfiguration = KafkaConfiguration.builder()
//                .withBootstrapServer(kafka.getBootstrapServers())
//                .withClientId("testclient")
//                .withApplicationId("123")
//                .withGroupId("testgroup")
//                .build();
//
//        StatusSplitStream statusSplitStream = StatusSplitStream.builder()
//                .withProps(kafkaConfiguration)
//                .build();
//        statusSplitStream.run();
//
//        StatusProducer statusProducer = StatusProducer.builder()
//                .withProps(kafkaConfiguration)
//                .build();
//
//        StatusEntity data = new StatusEntity();
//
//        data.setStatusType(StatusType.FAILURE);
//        statusProducer.send(data);
//
//        data.setStatusType(StatusType.SUCCESS);
//        statusProducer.send(data);
    }

//    @Test
    public void testStatusFailureConsumer() {
//        StatusFailureConsumer statusFailureConsumer = StatusFailureConsumer.builder()
//                .withProps(kafkaConfiguration)
//                .build();
//        StatusFailureConsumerResult statusFailureConsumerResult = statusFailureConsumer.consume();

    }

//    @Test
    public void testStatusSuccessConsumer() {
        // StatusSuccessConsumerResult statusSuccessConsumerResult =
        // StatusSuccessConsumer.builder()
        // .withProps(kafkaConfiguration)
        // .consume();
    }
}
