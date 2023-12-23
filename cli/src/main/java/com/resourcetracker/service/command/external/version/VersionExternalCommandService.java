package com.resourcetracker.service.command.external.version;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.model.ApplicationInfoResult;
import com.resourcetracker.service.client.command.HealthCheckClientCommandService;
import com.resourcetracker.service.client.command.VersionClientCommandService;
import com.resourcetracker.service.command.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents version external command service. */
@Service
public class VersionExternalCommandService implements ICommand {
  @Autowired PropertiesEntity properties;

  @Autowired private VersionClientCommandService versionClientCommandService;

  @Autowired private HealthCheckClientCommandService healthCheckClientCommandService;

  /**
   * @see ICommand
   */
  public void process() throws ApiServerException {
    try {
      ApplicationInfoResult applicationInfoResult = versionClientCommandService.process(null);

      System.out.printf(
          "API Server version: %s\n", applicationInfoResult.getExternalApi().getVersion());
    } finally {
      System.out.printf("Client version: %s\n", properties.getGitCommitId());
    }
  }
}
