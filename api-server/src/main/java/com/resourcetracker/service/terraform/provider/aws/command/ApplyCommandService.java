package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.terraform.provider.aws.common.AWSProviderConfigurationHelper;
//import com.resourcetracker.model.TerraformDeploymentApplicationCredentials;
import process.SProcess;
import process.SProcessExecutor;
import process.SProcessExecutor.OS;

import java.nio.file.Paths;

/**
 * Represents Terraform apply command.
 */
public class ApplyCommandService extends SProcess {
    private final String command;
    private final OS osType;

    public ApplyCommandService(
            com.resourcetracker.model.CredentialsFields credentials, PropertiesEntity properties) {
        this.osType = SProcessExecutor.getCommandExecutor().getOSType();

        this.command = "";
//        this.command = switch (osType){
//            case WINDOWS -> null;
//            case UNIX, MAC, ANY -> String.format(
//                    "terraform apply -chdir=%s %s %s -input=false -no-color -auto-approve",
//                    Paths.get(properties.getTerraformDirectory(), com.resourcetracker.model.Provider.AWS.toString()),
//                    AWSProviderConfigurationHelper.getBackendConfig(credentials),
//                    AWSProviderConfigurationHelper.getVariables("", properties.getGitCommitId())
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
