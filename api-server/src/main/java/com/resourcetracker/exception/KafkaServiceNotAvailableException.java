package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Arrays;
import java.util.Formatter;

public class KafkaServiceNotAvailableException extends IOException {
  public KafkaServiceNotAvailableException(Object... message) {
    super(
        new Formatter()
            .format("Kafka service is not available: %s", Arrays.stream(message).toArray())
            .toString());
  }
}
