package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.model.CredentialsFields;
import com.resourcetracker.model.Provider;
import com.resourcetracker.service.terraform.provider.aws.common.AWSProviderConfigurationHelper;
import java.nio.file.Paths;
import process.SProcess;
import process.SProcessExecutor;
import process.SProcessExecutor.OS;

/** Represents Terraform init command. Used before further deployment operation. */
public class InitCommandService extends SProcess {
  private final String command;
  private final OS osType;

  public InitCommandService(
      String workspaceUnitDirectory, CredentialsFields credentials, String terraformDirectory) {
    this.osType = SProcessExecutor.getCommandExecutor().getOSType();

    this.command =
        switch (osType) {
          case WINDOWS -> null;
          case UNIX, MAC, ANY -> String.format(
              "cd %s && %s terraform init %s -input=false -no-color -upgrade -reconfigure",
              Paths.get(terraformDirectory, Provider.AWS.toString()),
              AWSProviderConfigurationHelper.getEnvironmentVariables(
                  workspaceUnitDirectory, credentials),
              AWSProviderConfigurationHelper.getBackendConfig(credentials));
        };

    System.out.println(command);
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
