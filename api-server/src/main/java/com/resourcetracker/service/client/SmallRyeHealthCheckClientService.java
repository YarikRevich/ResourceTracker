package com.resourcetracker.service.client;

import com.resourcetracker.model.HealthCheckResult;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Represents client for SmallRye health check endpoints.
 */
@Path("/q")
@RegisterRestClient
public interface SmallRyeHealthCheckClientService {
  @GET
  @Path("/health")
  @Produces(MediaType.APPLICATION_JSON)
  HealthCheckResult qHealthGet();
}
