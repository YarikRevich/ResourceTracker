package com.resourcetracker.exception;

import java.io.IOException;

public class CommandExecutorException extends IOException {
    public CommandExecutorException() {
        super("Invalid command executor behaviour");
    }

    public CommandExecutorException(String message) {
        super(message);
    }
}
