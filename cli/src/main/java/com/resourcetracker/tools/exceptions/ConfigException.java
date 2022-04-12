package com.resourcetracker.tools.exceptions;

/**
 * Handles errors thrown in config class
 */
public class ConfigException extends Exception {
    public ConfigException(String errorMessage){
        super(errorMessage);
    }
}
