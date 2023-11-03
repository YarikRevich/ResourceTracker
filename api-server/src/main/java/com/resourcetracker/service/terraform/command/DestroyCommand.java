package com.resourcetracker.service.terraform.command;

import jakarta.enterprise.context.ApplicationScoped;
import process.SProcess;
import process.SProcessExecutor.OS;

@ApplicationScoped
public class DestroyCommand extends SProcess {
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
        return OS.UNIX;
    }
}
