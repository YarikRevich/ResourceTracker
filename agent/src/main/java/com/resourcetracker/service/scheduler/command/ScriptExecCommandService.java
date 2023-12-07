package com.resourcetracker.service.scheduler.command;

import com.resourcetracker.dto.ScriptExecCommandInputDto;
import org.springframework.stereotype.Service;
import process.SProcess;
import process.SProcessExecutor;

/**
 *
 */
@Service
public class ScriptExecCommandService extends SProcess {
    private ScriptExecCommandInputDto input;

    /**
     *
     * @param input
     */
    public void setInput(ScriptExecCommandInputDto input) {
        this.input = input;
    }

    /**
     * @return
     */
    @Override
    public String getCommand() {
        return input.getScript();
    }

    /**
     * @return
     */
    @Override
    public SProcessExecutor.OS getOSType() {
        return null;
    }
}
