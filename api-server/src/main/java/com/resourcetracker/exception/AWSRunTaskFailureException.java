package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Arrays;
import java.util.Formatter;

public class AWSRunTaskFailureException extends IOException {
  public AWSRunTaskFailureException(Object... message) {
    super(
        new Formatter()
            .format("AWS Task startup failed: %s", Arrays.stream(message).toArray())
            .toString());
  }
}
