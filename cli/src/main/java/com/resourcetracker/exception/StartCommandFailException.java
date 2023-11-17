package com.resourcetracker.exception;

import java.io.IOException;

public class StartCommandFailException extends IOException {
    public StartCommandFailException() {
        super("Start command failed to execute");
    }
}
