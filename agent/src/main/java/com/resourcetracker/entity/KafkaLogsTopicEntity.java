package com.resourcetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Setter
@AllArgsConstructor(staticName = "of")
public class KafkaLogsTopicEntity {
    UUID id;

    String data;

    String error;

    String hostName;

    String hostAddress;

    Timestamp timestamp;
}
