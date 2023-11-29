package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.entity.AWSDeploymentCredentialsEntity;
import com.resourcetracker.model.Provider;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.model.TerraformDeploymentApplicationCredentials;
import com.resourcetracker.service.terraform.provider.aws.common.AWSProviderConfigurationHelper;
import process.SProcess;
import process.SProcessExecutor;
import process.SProcessExecutor.OS;


import java.nio.file.Paths;

/**
 * Represents Terraform init command.
 * Used before further deployment operation.
 */
public class InitCommandService extends SProcess {
    private final String command;
    private final OS osType;

    public InitCommandService(TerraformDeploymentApplicationCredentials credentials, PropertiesEntity properties) {
        this.osType = SProcessExecutor.getCommandExecutor().getOSType();

        this.command = switch (osType){
            case WINDOWS -> null;
            case UNIX, MAC, ANY -> String.format(
                    "cd %s && terraform init %s -input=false -no-color -upgrade -reconfigure",
                    Paths.get(properties.getTerraformDirectory(), Provider.AWS.toString()),
                    AWSProviderConfigurationHelper.getBackendConfig(credentials)
            );
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
