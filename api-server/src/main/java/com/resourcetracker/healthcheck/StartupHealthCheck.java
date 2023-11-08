package com.resourcetracker.healthcheck;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Startup;

import jakarta.enterprise.context.ApplicationScoped;

@Startup
@ApplicationScoped
public class StartupHealthCheck implements HealthCheck {
    @Override
    public HealthCheckResponse call() {


        return HealthCheckResponse.up("Startup health check");
    }
}
