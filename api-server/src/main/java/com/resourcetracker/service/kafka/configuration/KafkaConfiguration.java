package com.resourcetracker.service.kafka.configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.resourcetracker.service.configuration.common.KafkaConfigurationBuilder;

public class KafkaConfiguration {
  public static KafkaConfigurationBuilder builder() {
    return new KafkaConfigurationBuilder();
  }
}
