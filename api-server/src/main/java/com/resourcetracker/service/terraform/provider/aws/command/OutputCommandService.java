package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.model.CredentialsFields;
import com.resourcetracker.model.Provider;
import com.resourcetracker.service.terraform.provider.aws.common.AWSProviderConfigurationHelper;
import java.nio.file.Paths;
import process.SProcess;
import process.SProcessExecutor;
import process.SProcessExecutor.OS;

/** Represents Terraform output command. Used after successful application of a deployment. */
public class OutputCommandService extends SProcess {
  private final String command;
  private final OS osType;

  public OutputCommandService(
      String workspaceUnitDirectory, CredentialsFields credentials, String terraformDirectory) {
    this.osType = SProcessExecutor.getCommandExecutor().getOSType();

    this.command =
        switch (osType) {
          case WINDOWS -> null;
          case UNIX, MAC, ANY -> String.format(
              "cd %s && %s terraform output -no-color -json",
              Paths.get(terraformDirectory, Provider.AWS.toString()),
              AWSProviderConfigurationHelper.getEnvironmentVariables(
                  workspaceUnitDirectory, credentials));
        };
  }

  @Override
  public String getCommand() {
    return command;
  }

  @Override
  public OS getOSType() {
    return osType;
  }
}
