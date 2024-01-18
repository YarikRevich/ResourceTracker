package com.resourcetracker.service.healthcheck.health;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.*;

@Liveness
@ApplicationScoped
public class HealthCheckService implements HealthCheck {
  @Override
  public HealthCheckResponse call() {
    HealthCheckResponseBuilder healthCheckResponse =
        HealthCheckResponse.named("Terraform application availability");

    healthCheckResponse.up();

    return healthCheckResponse.build();
  }
}
