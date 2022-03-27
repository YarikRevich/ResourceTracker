package com.resourcetrackersdk.tools.exceptions;

/**
 * Handles errors thrown in config class
 */
public class ConfigError extends Exception {
    public ConfigError(String errorMessage){
        super(errorMessage);
    }
}
