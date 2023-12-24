package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.model.CredentialsFields;
import process.SProcess;
import process.SProcessExecutor;
import process.SProcessExecutor.OS;

/** Represents Terraform apply command. */
public class ApplyCommandService extends SProcess {
  private final String command;
  private final OS osType;

  public ApplyCommandService(
      String agentContext,
      String workspaceUnitDirectory,
      CredentialsFields credentials,
      String terraformDirectory,
      String gitCommitId) {
    this.osType = SProcessExecutor.getCommandExecutor().getOSType();

    this.command = "";
    //        this.command = switch (osType){
    //            case WINDOWS -> null;
    //            case UNIX, MAC, ANY -> String.format(
    //                    "%s terraform apply -chdir=%s %s -input=false -no-color -auto-approve",
    //                    AWSProviderConfigurationHelper.getEnvironmentVariables(credentials),
    //                    Paths.get(properties.getTerraformDirectory(),
    // com.resourcetracker.model.Provider.AWS.toString()),
    //                    AWSProviderConfigurationHelper.getVariables("",
    // properties.getGitCommitId())
    //            );
    //        };
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
