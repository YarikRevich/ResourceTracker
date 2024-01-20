package com.resourcetracker.service.client.kafkastarter.common;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.io.Closeable;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/** Represents client for Kafka starter endpoints. */
@RegisterRestClient
public interface IKafkaStarterClientService extends Closeable {
  @POST
  @Path("/deploy")
  void qDeployPost(@QueryParam("host") String host);
}
