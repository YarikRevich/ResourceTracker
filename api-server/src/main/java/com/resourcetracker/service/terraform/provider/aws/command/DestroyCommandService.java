package com.resourcetracker.service.terraform.provider.aws.command;

import com.resourcetracker.entity.PropertiesEntity;
import process.SProcess;
import process.SProcessExecutor.OS;

public class DestroyCommandService extends SProcess {
    private final PropertiesEntity properties;

    public DestroyCommandService(PropertiesEntity properties) {
        this.properties = properties;
    }

    @Override
    public String getCommand() {

//        procService
//                .build()
//                .setCommand("terraform")
//                .setFlag("-chdir", this.getDirectory())
//                .setEnvVars(this.getEnvVars())
//                .setCommand("destroy")
//                .setPositionalVar("-no-color")
//                .run();

        return "";
    }

    @Override
    public OS getOSType() {
        return OS.ANY;
    }
}
