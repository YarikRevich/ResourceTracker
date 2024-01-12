package com.resourcetracker;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.client.command.*;
import com.resourcetracker.service.command.BaseCommandService;
// import com.resourcetracker.service.KafkaConsumerWrapper;
import com.resourcetracker.service.command.external.start.StartExternalCommandService;
import com.resourcetracker.service.command.external.start.provider.aws.AWSStartExternalCommandService;
import com.resourcetracker.service.command.external.state.StateExternalCommandService;
import com.resourcetracker.service.command.external.stop.StopExternalCommandService;
import com.resourcetracker.service.command.external.stop.provider.aws.AWSStopExternalCommandService;
import com.resourcetracker.service.command.external.version.VersionExternalCommandService;
import com.resourcetracker.service.command.internal.health.HealthCheckInternalCommandService;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.config.common.ValidConfigService;
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
  StateExternalCommandService.class,
  StopExternalCommandService.class,
  VersionExternalCommandService.class,
  HealthCheckInternalCommandService.class,
  ApplyClientCommandService.class,
  DestroyClientCommandService.class,
  HealthCheckClientCommandService.class,
  LogsClientCommandService.class,
  ScriptAcquireClientCommandService.class,
  SecretsAcquireClientCommandService.class,
  VersionClientCommandService.class,
  AWSStartExternalCommandService.class,
  AWSStopExternalCommandService.class,
  ConfigService.class,
  ValidConfigService.class,
  BuildProperties.class,
  PropertiesEntity.class
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
