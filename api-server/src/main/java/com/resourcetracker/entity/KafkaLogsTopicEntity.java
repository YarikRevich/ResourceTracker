package com.resourcetracker.entity;

import lombok.Getter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
public class KafkaLogsTopicEntity {
    UUID id;

    String data;

    String error;

    String hostName;

    String hostAddress;

    Timestamp timestamp;
}
