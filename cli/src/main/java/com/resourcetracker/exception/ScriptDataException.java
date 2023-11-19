package com.resourcetracker.exception;

import java.io.IOException;

/**
 * Represents exception, when given file and
 */
public class ScriptDataException extends IOException {
    public ScriptDataException() {
        super("Given explicit script or file with script is not present or incorrect");
    }
}
