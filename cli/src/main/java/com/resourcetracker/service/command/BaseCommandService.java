package com.resourcetracker.service.command;

import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.service.command.external.start.StartExternalCommandService;
import com.resourcetracker.service.command.external.state.StateExternalCommandService;
import com.resourcetracker.service.command.external.stop.StopExternalCommandService;
import com.resourcetracker.service.command.external.version.VersionExternalCommandService;
import com.resourcetracker.service.command.internal.healthcheck.HealthCheckInternalCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine.Command;

/** Represents command management service. */
@Service
@Command(
    name = "help",
    mixinStandardHelpOptions = true,
    description = "Cloud-based remote resource tracker",
    version = "1.0")
public class BaseCommandService {
  private static final Logger logger = LogManager.getLogger(BaseCommandService.class);

  @Autowired private StartExternalCommandService startCommandService;

  @Autowired private StateExternalCommandService stateCommandService;

  @Autowired private StopExternalCommandService stopCommandService;

  @Autowired private VersionExternalCommandService versionCommandService;

  @Autowired private HealthCheckInternalCommandService healthCheckInternalCommandService;

  /** Provides access to start command service. */
  @Command(description = "Start remote requests execution")
  private void start() {
    try {
      healthCheckInternalCommandService.process();

      startCommandService.process();
    } catch (ApiServerException e) {
      logger.fatal(e.getMessage());
    }
  }

  /** Provides access to state command service. */
  @Command(description = "Retrieve state of remote requests executions")
  private void state() {
    try {
      healthCheckInternalCommandService.process();

      stateCommandService.process();
    } catch (ApiServerException e) {
      logger.fatal(e.getMessage());
    }
  }

  /** Provides access to stop command service. */
  @Command(description = "Stop remote requests execution")
  private void stop() {
    try {
      healthCheckInternalCommandService.process();

      stopCommandService.process();
    } catch (ApiServerException e) {
      logger.fatal(e.getMessage());
    }
  }

  /** Provides access to version command service. */
  @Command(
      description =
          "Retrieve version of ResourceTracker CLI and ResourceTracker API Server(if available)")
  private void version() {
    try {
      versionCommandService.process();
    } catch (ApiServerException e) {
      logger.fatal(e.getMessage());
    }
  }
}
