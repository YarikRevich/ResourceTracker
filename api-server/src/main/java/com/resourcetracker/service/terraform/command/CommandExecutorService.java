package com.resourcetracker.service.terraform.command;

import jakarta.enterprise.context.ApplicationScoped;
import process.SProcessExecutor;

@ApplicationScoped
public class CommandExecutorService {
    private final SProcessExecutor processExecutor;

    CommandExecutorService() {
        this.processExecutor = SProcessExecutor.getCommandExecutor();
    }
}
