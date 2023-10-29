package com.resourcetracker.service.terraform.command;

import org.springframework.stereotype.Service;

@Service
public class InitCommand extends Command {
    @Override
    public String getCommand() {
//        procService
//                .build()
//                .setDirectory(this.getDirectory())
//                .setCommand("terraform")
//                .setEnvVars(this.getEnvVars())
//                .setCommand("init")
//                .setMapOfFlag("-backend-config", this.getBackendConfig())
//                .setPositionalVar("-no-color")
//                .setPositionalVar("-upgrade")
//                .setPositionalVar("-reconfigure")
//                .run();

        return "";
    }

    @Override
    public OS getOSType() {
        return OS.UNIX;
    }
}
