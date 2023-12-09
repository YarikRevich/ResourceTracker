package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Formatter;

public class CronExpressionException extends IOException {
  public CronExpressionException(Object... message) {
    super(new Formatter().format("Invalid cron exception received", message).toString());
  }
}
