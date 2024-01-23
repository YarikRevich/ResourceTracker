package com.resourcetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Represents ECS Kafka Task definition registration dto. */
@Getter
@AllArgsConstructor(staticName = "of")
public class AWSKafkaTaskDefinitionRegistrationDto {
  /** Represents Kafka Docker container image. */
  private final String kafkaContainerImage;

  /** Represents Kafka Docker container image version. */
  private final String kafkaVersion;

  /** Represents Kafka Docker container name. */
  private final String kafkaContainerName;

  /** Represents Kafka main port used in Docker container. */
  private final Integer kafkaContainerMainPort;

  /** Represents Kafka starter port used in Docker container. */
  private final Integer kafkaContainerStarterPort;

  /** Represents Kafka host used in a Docker container. */
  private final String kafkaHost;

  /** Represents alias used for Kafka host. */
  private final String kafkaHostAlias;

  /** Represents Kafka topic used for the deployment. */
  private final String kafkaCreateTopic;

  /** Represents alias used for Kafka topic creation. */
  private final String kafkaCreateTopicsAlias;

  /** Represents Kafka partitions used for the deployment. */
  private final String kafkaPartitions;

  /** Represents alias used for Kafka partitions creation. */
  private final String kafkaPartitionsAlias;
}
