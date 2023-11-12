package com.resourcetracker.service.terraform.command;

import com.resourcetracker.entity.ConfigEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import process.SProcess;
import process.SProcessExecutor.OS;

@ApplicationScoped
public class ApplyCommandService extends SProcess {
    @Inject
    ConfigEntity config;

    public void setTerraformDirectory() {
    }

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

        return String.format("cd %s && terraform validate", config.getTerraformDirectory());
    }

    @Override
    public OS getOSType() {
        return OS.UNIX;
    }
}
