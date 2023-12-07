package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Formatter;

public class ContainerStartupFailureException extends IOException {
    public ContainerStartupFailureException(Object... message) {
        super(new Formatter().format("Container execution startup failed", message).toString());
    }
}
