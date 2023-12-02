package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.model.Provider;
import com.resourcetracker.model.TerraformDeploymentApplicationCredentials;
import com.resourcetracker.service.terraform.provider.aws.common.AWSProviderConfigurationHelper;
import process.SProcess;
import process.SProcessExecutor;
import process.SProcessExecutor.OS;

import java.nio.file.Paths;

/**
 * Represents Terraform destroy command.
 */
public class DestroyCommandService extends SProcess {
    private final String command;
    private final OS osType;

    public DestroyCommandService(TerraformDeploymentApplicationCredentials credentials, PropertiesEntity properties) {
        this.osType = SProcessExecutor.getCommandExecutor().getOSType();

        this.command = switch (osType){
            case WINDOWS -> null;
            case UNIX, MAC, ANY -> String.format(
                    "%s terraform destroy %s -chdir=%s -input=false -auto-approve -no-color",
                    AWSProviderConfigurationHelper.getEnvironmentVariables(credentials),
                    AWSProviderConfigurationHelper.getBackendConfig(credentials),
                    Paths.get(properties.getTerraformDirectory(), Provider.AWS.toString())
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
