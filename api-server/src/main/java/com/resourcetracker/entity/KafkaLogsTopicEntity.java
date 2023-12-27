package com.resourcetracker.entity;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.Getter;

/** Represents message received from Kafka 'logs' topic. */
@Getter
public class KafkaLogsTopicEntity {
  private UUID id;

  private String data;

  private String error;

  private String hostName;

  private String hostAddress;

  private Timestamp timestamp;
}
