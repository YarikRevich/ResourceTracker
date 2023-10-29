package com.resourcetracker.service.kafka.consumer.entity;

public class FiveLastLogsConsumerResult {
  private String[] fiveLastlogs;

  public String[] getFiveLastlogs() {
    return this.fiveLastlogs;
  }

  public void setFiveLastlogs(String[] fiveLastlogs) {
    this.fiveLastlogs = fiveLastlogs;
  }
}
