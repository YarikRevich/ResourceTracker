package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.entity.AWSDeploymentCredentialsEntity;
import com.resourcetracker.model.Provider;
import com.resourcetracker.entity.PropertiesEntity;
import process.SProcess;
import process.SProcessExecutor;
import process.SProcessExecutor.OS;


import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 *
 */
public class InitCommandService extends SProcess {
    private final String command;
    private final OS osType;

//    private final PropertiesEntity properties;
//
//    private final AWSDeploymentCredentialsEntity credentials;
    //  private void selectEnvVars(){
//    terraformAPIService.setEnvVar(Constants.AWS_SHARED_CREDENTIALS_FILE, configEntity.getCloud().getCredentials());
//    terraformAPIService.setEnvVar(Constants.AWS_PROFILE, configEntity.getCloud().getProfile());
//    terraformAPIService.setEnvVar(Constants.AWS_REGION, configEntity.getCloud().getRegion());
//  }
//
//  private void selectVars(){
//    terraformAPIService.setVar(Constants.TERRAFORM_CONTEXT_VAR, configEntity.toJSONAsContext());
//  }
//

    public InitCommandService(AWSDeploymentCredentialsEntity credentials, PropertiesEntity properties) {
        this.osType = SProcessExecutor.getCommandExecutor().getOSType();

        this.command = switch (osType){
            case WINDOWS -> null;
            case UNIX, MAC, ANY -> String.format(
                    "cd %s && terraform init -input=false -no-color -upgrade -reconfigure",
                    Paths.get(properties.getTerraformDirectory(), Provider.AWS.toString())
            );
        };
    }

    /**
     * Composes backend configuration for s3 initialization.
     * @param credentials AWS vendor credentials to perform operation.
     * @return composed backend configuration.
     */
    private String getBackendConfig(AWSDeploymentCredentialsEntity credentials) {
        return new HashMap<String, String>() {{
            put("region", credentials.getRegion());
        }}
                .entrySet()
                .stream()
                .map(element -> String.format(
                        "-backend-config='%s=%s'",
                        element.getKey(),
                        element.getValue()))
                .collect(Collectors.joining(" "));
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
