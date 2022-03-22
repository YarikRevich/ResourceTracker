package com.resourcetracker.tools.exceptions;

/**
 * Handles exceptions thrown by the cloud components
 * 
 * @author YarikRevich
 */
public class CloudError extends Exception {
    public CloudError(String errorMessage){
        super(errorMessage);
    }
}
