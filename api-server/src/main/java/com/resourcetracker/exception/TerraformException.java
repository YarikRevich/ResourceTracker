package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Arrays;
import java.util.Formatter;

public class TerraformException extends IOException {
  public TerraformException(Object... message) {
    super(
        new Formatter()
            .format("Invalid Terraform related behaviour: %s", Arrays.stream(message).toArray())
            .toString());
  }
}
