package com.resourcetracker.resource;

import com.resourcetracker.api.HealthCheckResourceApi;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.model.HealthCheckResult;
import com.resourcetracker.model.HealthCheckStatus;
import com.resourcetracker.service.client.SmallRyeHealthCheckClientService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.WebApplicationException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.apache.http.HttpHost;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class HealthCheckResource implements HealthCheckResourceApi {
  @Inject
  @RestClient
  SmallRyeHealthCheckClientService smallRyeHealthCheckClientService;

  @Override
  public HealthCheckResult v1HealthGet() {
    try {
      return smallRyeHealthCheckClientService.qHealthGet();
    } catch (WebApplicationException e) {
      return e.getResponse().readEntity(HealthCheckResult.class);
    }
  }
}
