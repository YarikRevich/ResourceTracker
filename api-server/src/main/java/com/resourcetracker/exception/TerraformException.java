package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Formatter;

public class TerraformException extends IOException {
    public TerraformException(Object... message) {
        super(new Formatter().format("Invalid Terraform deployment behaviour", message).toString());
    }
}
