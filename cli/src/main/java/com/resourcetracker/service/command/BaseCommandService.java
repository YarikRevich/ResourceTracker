package com.resourcetracker.service.command;

import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.service.command.external.StartExternalCommandService;
import com.resourcetracker.service.command.external.StateExternalCommandService;
import com.resourcetracker.service.command.external.StopExternalCommandService;
import com.resourcetracker.service.command.external.VersionExternalCommandService;
import com.resourcetracker.service.command.internal.HealthCheckInternalCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine.Command;

/**
 * Represents command management service.
 */
@Service
@Command(
    name = "help",
    mixinStandardHelpOptions = true,
    description = "Cloud-based remote resource tracker",
    version = "1.0")
public class BaseCommandService {
  @Autowired private StartExternalCommandService startCommandService;

  @Autowired private StateExternalCommandService stateCommandService;

  @Autowired private StopExternalCommandService stopCommandService;

  @Autowired private VersionExternalCommandService versionCommandService;

  @Autowired private HealthCheckInternalCommandService healthCheckInternalCommandService;

  /**
   * Provides access to start command service.
   */
  @Command(description = "Start remote requests execution")
  void start() throws ApiServerNotAvailableException {
    healthCheckInternalCommandService.process();

    startCommandService.process();
  }

  /**
   * Provides access to state command service.
   */
  @Command(description = "Retrieve state of remote requests executions")
  void state() throws ApiServerNotAvailableException {
    stateCommandService.process();
  }

  /**
   * Provides access to stop command service.
   */
  @Command(description = "Stop remote requests execution")
  void stop() throws ApiServerNotAvailableException {
    stopCommandService.process();
  }

  /**
   * Provides access to version command service.
   */
  @Command(description = "Stop remote requests execution")
  void version() throws ApiServerNotAvailableException {
    versionCommandService.process();
  }
}
