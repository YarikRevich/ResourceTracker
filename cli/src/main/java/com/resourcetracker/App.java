package com.resourcetracker;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.client.command.*;
import com.resourcetracker.service.command.BaseCommandService;
// import com.resourcetracker.service.KafkaConsumerWrapper;
import com.resourcetracker.service.command.external.start.StartExternalCommandService;
import com.resourcetracker.service.command.external.start.provider.aws.AWSStartExternalCommandService;
import com.resourcetracker.service.command.external.state.StateExternalCommandService;
import com.resourcetracker.service.command.external.state.provider.aws.AWSStateExternalCommandService;
import com.resourcetracker.service.command.external.stop.StopExternalCommandService;
import com.resourcetracker.service.command.external.stop.provider.aws.AWSStopExternalCommandService;
import com.resourcetracker.service.command.external.version.VersionExternalCommandService;
import com.resourcetracker.service.command.internal.health.HealthCheckInternalCommandService;
import com.resourcetracker.service.command.internal.readiness.ReadinessCheckInternalCommandService;
import com.resourcetracker.service.command.internal.readiness.provider.aws.AWSReadinessCheckInternalCommandService;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.config.common.ValidConfigService;
import com.resourcetracker.service.visualization.VisualizationService;
import com.resourcetracker.service.visualization.common.label.StartCommandVisualizationLabel;
import com.resourcetracker.service.visualization.common.label.StateCommandVisualizationLabel;
import com.resourcetracker.service.visualization.common.label.StopCommandVisualizationLabel;
import com.resourcetracker.service.visualization.common.label.VersionCommandVisualizationLabel;
import com.resourcetracker.service.visualization.state.VisualizationState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
@Import({
  BaseCommandService.class,
  StartExternalCommandService.class,
  AWSStateExternalCommandService.class,
  StateExternalCommandService.class,
  StopExternalCommandService.class,
  VersionExternalCommandService.class,
  HealthCheckInternalCommandService.class,
  ReadinessCheckInternalCommandService.class,
  AWSReadinessCheckInternalCommandService.class,
  ApplyClientCommandService.class,
  DestroyClientCommandService.class,
  HealthCheckClientCommandService.class,
  ReadinessCheckClientCommandService.class,
  LogsClientCommandService.class,
  ScriptAcquireClientCommandService.class,
  SecretsAcquireClientCommandService.class,
  VersionClientCommandService.class,
  AWSStartExternalCommandService.class,
  AWSStopExternalCommandService.class,
  ConfigService.class,
  ValidConfigService.class,
  BuildProperties.class,
  PropertiesEntity.class,
  StartCommandVisualizationLabel.class,
  StopCommandVisualizationLabel.class,
  StateCommandVisualizationLabel.class,
  VersionCommandVisualizationLabel.class,
  VisualizationService.class,
  VisualizationState.class
})
public class App implements ApplicationRunner, ExitCodeGenerator {
  private static final Logger logger = LogManager.getLogger(App.class);

  private int exitCode;

  @Autowired private ValidConfigService validConfigService;

  @Autowired private BaseCommandService baseCommandService;

  @Override
  public void run(ApplicationArguments args) {
    try {
      validConfigService.validate();
    } catch (Exception e) {
      logger.fatal(e.getMessage());
      return;
    }

    CommandLine cmd = new CommandLine(baseCommandService);
    exitCode = cmd.execute(args.getSourceArgs());
  }

  @Override
  public int getExitCode() {
    return exitCode;
  }
}
