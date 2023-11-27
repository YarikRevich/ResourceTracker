package com.resourcetracker.healthcheck;

import com.resourcetracker.service.kafka.KafkaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.*;

@Liveness
@ApplicationScoped
public class KafkaHealthCheck implements HealthCheck {
    @Inject
    KafkaService kafkaService;

    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder healthCheckResponse = HealthCheckResponse.named("Kafka connection availability");

        if (kafkaService.isConnected()){
            healthCheckResponse.up();
        } else {
            healthCheckResponse.down();
        }

        return healthCheckResponse.build();
    }
}
