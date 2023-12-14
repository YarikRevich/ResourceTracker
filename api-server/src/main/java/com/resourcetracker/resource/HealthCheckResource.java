package com.resourcetracker.resource;

import com.resourcetracker.api.HealthCheckResourceApi;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.model.HealthCheckResult;
import com.resourcetracker.model.HealthCheckStatus;
import com.resourcetracker.service.client.SmallRyeHealthCheckClientService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.WebApplicationException;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@ApplicationScoped
public class HealthCheckResource implements HealthCheckResourceApi {
  @Inject PropertiesEntity properties;

  /**
   * @return
   */
  @Override
  public HealthCheckResult v1HealthGet() {
    URL url;
    try {
      url = URI.create(String.format(
              "%s://%s:%d",
              HttpHost.DEFAULT_SCHEME_NAME,
              properties.getApplicationHost(),
              properties.getApplicationPort())).toURL();
    } catch (MalformedURLException e) {
      throw new InternalServerErrorException();
    }

    SmallRyeHealthCheckClientService smallRyeHealthCheckClientService =
        RestClientBuilder.newBuilder()
            .baseUrl(url)
            .build(SmallRyeHealthCheckClientService.class);

    HealthCheckResult result;
    try {
      result = smallRyeHealthCheckClientService.qHealthGet();
    } catch (WebApplicationException e) {
        return e.getResponse().readEntity(HealthCheckResult.class);
    }
    return result;
  }
}
