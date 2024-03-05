package com.resourcetracker.resource;

import com.resourcetracker.api.HealthResourceApi;
import com.resourcetracker.converter.HealthCheckResponseToReadinessCheckResult;
import com.resourcetracker.entity.InternalConfigEntity;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.WorkspaceUnitDirectoryNotFoundException;
import com.resourcetracker.exception.WorkspaceUnitInternalConfigFileNotFoundException;
import com.resourcetracker.model.HealthCheckResult;
import com.resourcetracker.model.ReadinessCheckApplication;
import com.resourcetracker.model.ReadinessCheckResult;
import com.resourcetracker.service.client.smallrye.ISmallRyeHealthCheckClientService;
import com.resourcetracker.service.healthcheck.readiness.ReadinessCheckService;
import com.resourcetracker.service.terraform.workspace.WorkspaceService;
import com.resourcetracker.service.terraform.workspace.facade.WorkspaceFacade;
import io.quarkus.vertx.http.HttpServerOptionsCustomizer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import lombok.SneakyThrows;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class HealthResource implements HealthResourceApi {
  @Inject PropertiesEntity properties;

  @Inject WorkspaceFacade workspaceFacade;

  @Inject WorkspaceService workspaceService;

  @Inject @RestClient ISmallRyeHealthCheckClientService smallRyeHealthCheckClientService;

  @Override
  public HealthCheckResult v1HealthGet() {
    try {
      return smallRyeHealthCheckClientService.qHealthGet();
    } catch (WebApplicationException e) {
      return e.getResponse().readEntity(HealthCheckResult.class);
    }
  }

  @Override
  @SneakyThrows
  public ReadinessCheckResult v1ReadinessPost(ReadinessCheckApplication readinessCheckApplication) {
    String workspaceUnitKey =
        workspaceFacade.createUnitKey(
            readinessCheckApplication.getProvider(), readinessCheckApplication.getCredentials());

    if (!workspaceService.isUnitDirectoryExist(workspaceUnitKey)) {
      throw new WorkspaceUnitDirectoryNotFoundException();
    }

    String workspaceUnitDirectory = workspaceService.getUnitDirectory(workspaceUnitKey);

    if (!workspaceService.isInternalConfigFileExist(workspaceUnitDirectory)) {
      throw new WorkspaceUnitInternalConfigFileNotFoundException();
    }

    InternalConfigEntity internalConfig =
        workspaceService.getInternalConfigFileContent(workspaceUnitDirectory);

    ReadinessCheckService readinessCheckService =
        new ReadinessCheckService(internalConfig, properties);

    return HealthCheckResponseToReadinessCheckResult.convert(readinessCheckService.call());
  }
}
