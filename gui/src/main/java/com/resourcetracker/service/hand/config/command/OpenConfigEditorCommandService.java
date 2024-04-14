package com.resourcetracker.service.hand.config.command;

import java.nio.file.Paths;
import process.SProcess;
import process.SProcessExecutor;

/** Represents command, which is responsible for a startup of the configuration file editor. */
public class OpenConfigEditorCommandService extends SProcess {
  private final String command;
  private final SProcessExecutor.OS osType;

  public OpenConfigEditorCommandService(String configDirectory) {
    this.osType = SProcessExecutor.getCommandExecutor().getOSType();

    this.command =
        switch (osType) {
          case MAC -> String.format(
              "open -eW %s",
                  configDirectory);
          case WINDOWS, UNIX, ANY -> null;
        };
  }

  @Override
  public String getCommand() {
    return command;
  }

  @Override
  public SProcessExecutor.OS getOSType() {
    return osType;
  }
}
