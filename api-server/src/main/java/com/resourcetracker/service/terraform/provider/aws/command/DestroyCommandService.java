package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.model.CredentialsFields;
import com.resourcetracker.service.terraform.common.TerraformConfigurationHelper;
import com.resourcetracker.service.terraform.provider.aws.common.AWSProviderConfigurationHelper;
import java.nio.file.Paths;
import process.SProcess;
import process.SProcessExecutor;
import process.SProcessExecutor.OS;

/** Represents Terraform destroy command. */
public class DestroyCommandService extends SProcess {
  private final String command;
  private final OS osType;

  public DestroyCommandService(
      String agentContext,
      String workspaceUnitDirectory,
      CredentialsFields credentials,
      String terraformDirectory,
      String gitCommitId) {
    this.osType = SProcessExecutor.getCommandExecutor().getOSType();

    this.command =
        switch (osType) {
          case WINDOWS -> null;
          case UNIX, MAC, ANY -> String.format(
              "cd %s && %s %s terraform destroy -input=false -auto-approve -no-color",
              Paths.get(terraformDirectory, com.resourcetracker.model.Provider.AWS.toString()),
              AWSProviderConfigurationHelper.getEnvironmentVariables(
                  workspaceUnitDirectory, credentials),
              TerraformConfigurationHelper.getContentEnvironmentVariables(
                  agentContext, gitCommitId));
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
