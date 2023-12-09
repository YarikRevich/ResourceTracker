package com.resourcetracker.service.resource.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import picocli.CommandLine.Command;

@Service
@Command(
    name = "help",
    mixinStandardHelpOptions = true,
    description = "Cloud-based remote resource tracker",
    version = "1.0")
public class BaseCommandService {
  @Autowired private StartCommandService startCommandService;

  @Autowired private StateCommandService stateCommandService;

  @Autowired private StopCommandService stopCommandService;

  @Autowired private VersionCommandService versionCommandService;

  @Command(description = "Start remote requests execution")
  void start() {
    startCommandService.process();
  }

  @Command(description = "Retrieve state of remote requests executions")
  void state() {
    stateCommandService.process();
  }

  @Command(description = "Stop remote requests execution")
  void stop() {
    stopCommandService.process();
  }

  @Command(description = "Stop remote requests execution")
  void version() {
    versionCommandService.process();
  }
}
