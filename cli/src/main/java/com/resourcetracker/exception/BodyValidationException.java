package com.resourcetracker.exception;

import java.io.IOException;

public class BodyValidationException extends IOException {
    public BodyValidationException() {
        super("Received body is null");
    }
}
