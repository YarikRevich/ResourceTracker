package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Formatter;

public class KafkaProducerSendException extends IOException {
  public KafkaProducerSendException(Object... message) {
    super(new Formatter().format("Error happened sending message", message).toString());
  }
}
