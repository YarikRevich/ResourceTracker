package com.resourcetracker.entity;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.Getter;

/** Represents message received from Kafka 'logs' topic. */
@Getter
public class KafkaLogsTopicEntity {
  UUID id;

  String data;

  String error;

  String hostName;

  String hostAddress;

  Timestamp timestamp;
}
