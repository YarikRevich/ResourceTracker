package com.resourcetracker.healthcheck;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.*;

@Liveness
@ApplicationScoped
public class LivenessHealthCheck implements HealthCheck {
    @Override
    public HealthCheckResponse call() {
        HealthCheckResponseBuilder healthCheckResponseBuilder = HealthCheckResponse.builder();



        return HealthCheckResponse.up("Live health check");
    }
}
