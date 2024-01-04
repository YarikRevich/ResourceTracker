package com.resourcetracker.service.hand.config.command;

import com.resourcetracker.entity.PropertiesEntity;
import java.nio.file.Paths;
import process.SProcess;
import process.SProcessExecutor;

/**
 * Represents command, which is responsible for a startup of the API Server in the background as
 * system service.
 */
public class OpenConfigEditorCommandService extends SProcess {
  private final String command;
  private final SProcessExecutor.OS osType;

  public OpenConfigEditorCommandService(PropertiesEntity properties) {
    this.osType = SProcessExecutor.getCommandExecutor().getOSType();

    this.command =
        switch (osType) {
          case MAC -> String.format(
              "open -e %s",
              Paths.get(
                  System.getProperty("user.home"),
                  properties.getConfigRootPath(),
                  properties.getConfigUserFilePath()));
          case WINDOWS, UNIX, ANY -> null;
        };

    System.out.println(command);
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
