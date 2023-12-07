package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Formatter;

public class InvalidBootstrapServerEnvironmentVariableException extends IOException {
    public InvalidBootstrapServerEnvironmentVariableException(Object... message) {
        super(new Formatter().format("Invalid boostrap server address given", message).toString());
    }
}
