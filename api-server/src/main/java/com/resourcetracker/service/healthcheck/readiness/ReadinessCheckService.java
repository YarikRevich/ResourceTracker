package com.resourcetracker.service.healthcheck.readiness;

import com.resourcetracker.entity.InternalConfigEntity;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.kafka.KafkaService;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;

/** Checks if the Kafka service is available for the given workspace. */
public class ReadinessCheckService implements HealthCheck {
  private final KafkaService kafkaService;

  public ReadinessCheckService(InternalConfigEntity internalConfig, PropertiesEntity properties) {
    this.kafkaService = new KafkaService(internalConfig.getKafka().getHost(), properties);
  }

  @Override
  public HealthCheckResponse call() {
    HealthCheckResponseBuilder healthCheckResponse =
        HealthCheckResponse.named("Kafka connection availability");

    if (kafkaService.isConnected()) {
      healthCheckResponse.up();
    } else {
      healthCheckResponse.down();
    }

    return healthCheckResponse.build();
  }
}
