package com.resourcetracker.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine.Command;

@Service
@Command(name = "help", mixinStandardHelpOptions = true, description = "Cloud-based remote resource tracker", version = "1.0")
public class BaseCommandService {
  @Autowired
  private StartCommandService startCommandService;

  @Autowired
  private StateCommandService stateCommandService;

  @Autowired
  private StopCommandService stopCommandService;

  @Command(description = "Start ")
  void start() {
    startCommandService.process();
  }

  @Command(description = "")
  void state() {
    stateCommandService.process();
  }

  @Command(description = "")
  void stop() {
    stopCommandService.process();
  }
}
