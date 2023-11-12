package com.resourcetracker.exception;

import java.io.IOException;

public class SchedulerException extends IOException {
    public SchedulerException() {
        super("Invalid scheduler behaviour");
    }
}
