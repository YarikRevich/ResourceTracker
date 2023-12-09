package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Formatter;

public class SchedulerException extends IOException {
  public SchedulerException(Object... message) {
    super(new Formatter().format("Invalid scheduler behaviour", message).toString());
  }
}
