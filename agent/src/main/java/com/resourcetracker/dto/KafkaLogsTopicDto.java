package com.resourcetracker.dto;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Setter;

/** Represents message for general "logs" topic in Kafka cluster. */
@Setter
@AllArgsConstructor(staticName = "of")
public class KafkaLogsTopicDto {
  /** Represents unique identificator of a Kafka message. */
  public UUID id;

  /** Represents name of the request. */
  public String name;

  /** Represents stdout stream output of the executed command. */
  public String data;

  /** Represents stderr stream output of the executed command. */
  public String error;

  /** Represents hostname of the machine where ResourceTracker Agent gets started. */
  public String hostName;

  /** Represents host address of the machine where ResourceTracker Agent gets started. */
  public String hostAddress;

  /** Represents timestamp is the event when message gets sent to Kafka cluster. */
  public Timestamp timestamp;
}
