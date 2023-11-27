package com.resourcetracker.exception;

import java.io.IOException;

public class AWSRunTaskFailureException extends IOException {
    public AWSRunTaskFailureException(String message) {
        super(message);
    }
}
