package com.resourcetracker;

import com.resourcetracker.service.command.StartCommandService;
import com.resourcetracker.service.command.StateCommandService;
import com.resourcetracker.service.command.StopCommandService;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import com.resourcetracker.service.command.BaseCommandService;
//import com.resourcetracker.service.KafkaConsumerWrapper;

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
  int exitCode;

  @Autowired
  private BaseCommandService baseCommandService;

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
