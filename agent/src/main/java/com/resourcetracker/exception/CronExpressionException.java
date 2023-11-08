package com.resourcetracker.exception;

import java.io.IOException;

public class CronExpressionException extends IOException {
    public CronExpressionException() {
        super("Invalid cron exception received");
    }
}
