package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.entity.AWSDeploymentCredentialsEntity;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.model.Provider;
import com.resourcetracker.model.TerraformDeploymentApplicationCredentials;
import com.resourcetracker.service.terraform.provider.aws.common.AWSProviderConfigurationHelper;
import jakarta.enterprise.context.ApplicationScoped;
import process.SProcess;
import process.SProcessExecutor;
import process.SProcessExecutor.OS;

import java.nio.file.Paths;

/**
 * Represents Terraform output command.
 * Used after successful application of a deployment.
 */
public class OutputCommandService extends SProcess {
    private final String command;
    private final OS osType;

    public OutputCommandService(TerraformDeploymentApplicationCredentials credentials, PropertiesEntity properties) {
        this.osType = SProcessExecutor.getCommandExecutor().getOSType();

        this.command = switch (osType){
            case WINDOWS -> null;
            case UNIX, MAC, ANY -> String.format(
                    "cd %s && %s terraform output %s -no-color -json",
                    Paths.get(properties.getTerraformDirectory(), Provider.AWS.toString()),
                    AWSProviderConfigurationHelper.getEnvironmentVariables(credentials),
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
