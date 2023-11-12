package com.resourcetracker.exception;

import java.io.IOException;

/**
 * Handles exceptions thrown by the cloud deployment service
 *
 * @author YarikRevich
 */
public class TerraformException extends IOException {
    public TerraformException() {
        super("Invalid Terraform deployment behaviour");
    }

    public TerraformException(String message) {
        super(message);
    }
}
