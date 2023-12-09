package com.resourcetracker;

import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.resource.command.BaseCommandService;
// import com.resourcetracker.service.KafkaConsumerWrapper;
import com.resourcetracker.service.resource.command.StartCommandService;
import com.resourcetracker.service.resource.command.StateCommandService;
import com.resourcetracker.service.resource.command.StopCommandService;
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
  StartCommandService.class,
  StateCommandService.class,
  StopCommandService.class,
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
