package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Formatter;

public class InvalidAgentContextEnvironmentVariableException extends IOException {
    public InvalidAgentContextEnvironmentVariableException(Object... message) {
        super(new Formatter().format("Invalid agent context given", message).toString());
    }
}
