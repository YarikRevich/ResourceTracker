package com.resourcetracker;

import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.command.BaseCommandService;
// import com.resourcetracker.service.KafkaConsumerWrapper;
import com.resourcetracker.service.command.external.StartExternalCommandService;
import com.resourcetracker.service.command.external.StateExternalCommandService;
import com.resourcetracker.service.command.external.StopExternalCommandService;
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
  ConfigService.class,
  BuildProperties.class
})
public class App implements ApplicationRunner, ExitCodeGenerator {
  private int exitCode;

  @Autowired private BaseCommandService baseCommandService;

  @Override
  public void run(ApplicationArguments args) {
    CommandLine cmd = new CommandLine(baseCommandService);
    exitCode = cmd.execute(args.getSourceArgs());
  }

  @Override
  public int getExitCode() {
    return exitCode;
  }
}
