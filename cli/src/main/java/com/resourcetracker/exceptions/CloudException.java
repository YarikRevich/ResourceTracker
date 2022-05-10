package com.resourcetracker.tools.exceptions;

/**
 * Handles exceptions thrown by the cloud components
 *
 * @author YarikRevich
 */
public class CloudException extends Exception {
    public CloudException(String errorMessage){
        super(errorMessage);
    }
}
