package com.resourcetracker.service.terraform.command;

import org.springframework.stereotype.Service;

@Service
public class OutputCommand extends Command {
    @Override
    public String getCommand() {
//        procService
//                .build()
//                .setCommand("terraform")
//                .setFlag("-chdir", this.getDirectory())
//                .setEnvVars(this.getEnvVars())
//                .setCommand("output")
//                .setPositionalVar("-json")
//                .setPositionalVar("-no-color")
//                .run();
//
        return "";
    }

    @Override
    public OS getOSType() {
        return OS.UNIX;
    }
}
