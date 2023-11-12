package com.resourcetracker.service.executor;

import com.resourcetracker.entity.CommandExecutorOutputEntity;
import com.resourcetracker.exception.CommandExecutorException;
import org.springframework.stereotype.Service;
import process.SProcess;
import process.SProcessExecutor;
import process.exceptions.NonMatchingOSException;
import process.exceptions.SProcessNotYetStartedException;

import java.io.IOException;

@Service
public class CommandExecutorService {
    private final SProcessExecutor processExecutor;

    CommandExecutorService() {
        this.processExecutor = SProcessExecutor.getCommandExecutor();
    }

    public CommandExecutorOutputEntity executeCommand(SProcess command) throws CommandExecutorException {
        try {
            processExecutor.executeCommand(command);
        } catch (IOException | NonMatchingOSException e) {
            throw new CommandExecutorException(e.getMessage());
        }

        try {
            if (!command.waitForOutput()){
                throw new CommandExecutorException();
            }
        } catch (IOException e){
            throw new CommandExecutorException(e.getMessage());
        }

        String commandErrorOutput;

        try {
            commandErrorOutput = command.getErrorOutput();
        } catch (SProcessNotYetStartedException e) {
            throw new CommandExecutorException(e.getMessage());
        }

        String commandNormalOutput;

        try {
            commandNormalOutput = command.getNormalOutput();
        } catch (SProcessNotYetStartedException e) {
            throw new CommandExecutorException(e.getMessage());
        }

        return CommandExecutorOutputEntity.of(commandNormalOutput, commandErrorOutput);
    }
}
