package com.resourcetracker.healthcheck;

import com.resourcetracker.service.kafka.KafkaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.*;

@ApplicationScoped
public class AvailabilityHealthCheck {
    @Inject
    KafkaService kafkaService;

    @Liveness
    @Readiness
    public HealthCheckResponse kafkaConnection() {
        HealthCheckResponseBuilder healthCheckResponse = HealthCheckResponse.builder().name("Kafka connection availability");

        if (kafkaService.isConnected()){
            return healthCheckResponse.up().build();
        } else {
            return healthCheckResponse.down().build();
        }
    }
}
