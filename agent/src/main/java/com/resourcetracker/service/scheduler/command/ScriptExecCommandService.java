package com.resourcetracker.service.scheduler.command;

import org.springframework.stereotype.Service;
import process.SProcess;
import process.SProcessExecutor;

public class ScriptExecCommandService extends SProcess {
    private final String input;

    public ScriptExecCommandService(String input) {
        this.input = input;
    }

    /**
     * @return
     */
    @Override
    public String getCommand() {
        return input;
    }

    /**
     * @return
     */
    @Override
    public SProcessExecutor.OS getOSType() {
        return null;
    }
}
