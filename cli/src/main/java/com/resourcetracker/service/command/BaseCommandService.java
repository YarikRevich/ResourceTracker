package com.resourcetracker.service.command;

import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.service.command.external.start.StartExternalCommandService;
import com.resourcetracker.service.command.external.state.StateExternalCommandService;
import com.resourcetracker.service.command.external.stop.StopExternalCommandService;
import com.resourcetracker.service.command.external.version.VersionExternalCommandService;
import com.resourcetracker.service.command.internal.health.HealthCheckInternalCommandService;
import com.resourcetracker.service.command.internal.readiness.ReadinessCheckInternalCommandService;
import com.resourcetracker.service.visualization.VisualizationService;
import com.resourcetracker.service.visualization.common.label.StartCommandVisualizationLabel;
import com.resourcetracker.service.visualization.common.label.VersionCommandVisualizationLabel;
import com.resourcetracker.service.visualization.state.VisualizationState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine.Command;

/** Represents general command management service. */
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

  @Autowired private ReadinessCheckInternalCommandService readinessCheckInternalCommandService;

  @Autowired private StartCommandVisualizationLabel startCommandVisualizationLabel;

  @Autowired private VersionCommandVisualizationLabel versionCommandVisualizationLabel;

  @Autowired private VisualizationService visualizationService;

  @Autowired private VisualizationState visualizationState;

  /** Provides access to start command service. */
  @Command(description = "Start remote requests execution")
  private void start() {
    visualizationState.setLabel(startCommandVisualizationLabel);

    visualizationService.process();

    try {
      healthCheckInternalCommandService.process();

      startCommandService.process();
    } catch (ApiServerException e) {
      logger.fatal(e.getMessage());
      return;
    }

    visualizationService.await();
  }

  /** Provides access to state command service. */
  @Command(description = "Retrieve state of remote requests executions")
  private void state() {
    visualizationService.process();

    try {
      healthCheckInternalCommandService.process();
      readinessCheckInternalCommandService.process();

      stateCommandService.process();
    } catch (ApiServerException e) {
      logger.fatal(e.getMessage());
      return;
    }

    visualizationService.await();
  }

  /** Provides access to stop command service. */
  @Command(description = "Stop remote requests execution")
  private void stop() {
    visualizationService.process();

    try {
      healthCheckInternalCommandService.process();

      stopCommandService.process();
    } catch (ApiServerException e) {
      logger.fatal(e.getMessage());
      return;
    }

    visualizationService.await();
  }

  /** Provides access to version command service. */
  @Command(
      description =
          "Retrieve version of ResourceTracker CLI and ResourceTracker API Server(if available)")
  private void version() {
    visualizationState.setLabel(versionCommandVisualizationLabel);

    visualizationService.process();

    try {
      versionCommandService.process();
    } catch (ApiServerException e) {
      logger.fatal(e.getMessage());
      return;
    }

    visualizationService.await();
  }
}
