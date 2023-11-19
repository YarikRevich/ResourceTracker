package com.resourcetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Represents message for general "logs" topic in Kafka cluster.
 */
@Setter
@AllArgsConstructor(staticName = "of")
public class KafkaLogsTopicEntity {
    UUID id;

    /**
     * Represents stdout stream output of the executed command.
     */
    String data;

    /**
     * Represents stderr stream output of the executed command.
     */
    String error;

    /**
     * Represents hostname of the machine where ResourceTracker Agent gets started.
     */
    String hostName;

    /**
     * Represents hostaddress of the machine where ResourceTracker Agent gets started.
     */
    String hostAddress;

    /**
     * Represents timestamp is the event when message gets sent to Kafka cluster.
     */
    Timestamp timestamp;
}
