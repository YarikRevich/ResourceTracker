package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.entity.PropertiesEntity;
import jakarta.enterprise.context.ApplicationScoped;
import process.SProcess;
import process.SProcessExecutor.OS;

@ApplicationScoped
public class ApplyCommandService extends SProcess {
    private final PropertiesEntity properties;

    public ApplyCommandService(PropertiesEntity properties) {
        this.properties = properties;
    }

    private String getEnvironmentVariables() {
        return "";
    }

    private String getVariables() {
        return "";
    }


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
//  private void selectBackendConfig(){
//    terraformAPIService.setBackendConfig(Constants.TERRAFORM_BACKEND_CONFIG_SHARED_CREDENTIALS_FILE, configEntity.getCloud().getCredentials());
//    terraformAPIService.setBackendConfig(Constants.TERRAFORM_BACKEND_PROFILE, configEntity.getCloud().getProfile());
//  }

    @Override
    public String getCommand() {
//        procService
//                .build()
//                .setCommand("terraform")
//                .setFlag("-chdir", this.getDirectory())
//                .setEnvVars(this.getEnvVars())
//                .setCommand("apply")
//                .setMapOfFlag("-var", this.getVars())
//                .setPositionalVar("-auto-approve")
//                .setPositionalVar("-no-color")
//                .run();

        return String.format("cd %s && terraform validate", properties.getTerraformDirectory());
    }

    @Override
    public OS getOSType() {
        return OS.ANY;
    }
}
