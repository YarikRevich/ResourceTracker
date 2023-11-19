package com.resourcetracker.exception;

import java.io.IOException;

public class BodyIsNull extends IOException {
    public BodyIsNull() {
        super("Received body is null");
    }
}
