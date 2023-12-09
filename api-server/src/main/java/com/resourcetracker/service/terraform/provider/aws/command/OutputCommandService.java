package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.entity.PropertiesEntity;
// import com.resourcetracker.model.TerraformDeploymentApplicationCredentials;
import process.SProcess;
import process.SProcessExecutor;
import process.SProcessExecutor.OS;

/** Represents Terraform output command. Used after successful application of a deployment. */
public class OutputCommandService extends SProcess {
  private final String command;
  private final OS osType;

  public OutputCommandService(
      com.resourcetracker.model.CredentialsFields credentials, PropertiesEntity properties) {
    this.osType = SProcessExecutor.getCommandExecutor().getOSType();

    this.command = "";
    //
    //        this.command = switch (osType){
    //            case WINDOWS -> null;
    //            case UNIX, MAC, ANY -> String.format(
    //                    "cd %s && %s terraform output %s -no-color -json",
    //                    Paths.get(properties.getTerraformDirectory(), Provider.AWS.toString()),
    //                    AWSProviderConfigurationHelper.getEnvironmentVariables(credentials),
    //                    AWSProviderConfigurationHelper.getBackendConfig(credentials)
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
