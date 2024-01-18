package com.resourcetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Represents ECS Kafka Task definition registration dto. */
@Getter
@AllArgsConstructor(staticName = "of")
public class AWSKafkaTaskDefinitionRegistrationDto {
  /** Represents Kafka Docker container image. */
  private String kafkaContainerImage;

  /** Represents Kafka Docker container image version. */
  private String kafkaVersion;

  /** Represents Kafka Docker container name. */
  private String kafkaContainerName;

  /** Represents Kafka main port used in Docker container. */
  private Integer kafkaContainerMainPort;

  /** Represents Kafka starter port used in Docker container. */
  private Integer kafkaContainerStarterPort;

  /** Represents Kafka host used in a Docker container. */
  private String kafkaHost;

  /** Represents alias used for Kafka host. */
  private String kafkaHostAlias;

  /** Represents Kafka topic used for the deployment. */
  private String kafkaCreateTopic;

  /** Represents alias used for Kafka topic creation. */
  private String kafkaCreateTopicsAlias;

  /** Represents Kafka partitions used for the deployment. */
  private String kafkaPartitions;

  /** Represents alias used for Kafka partitions creation. */
  private String kafkaPartitionsAlias;
}
