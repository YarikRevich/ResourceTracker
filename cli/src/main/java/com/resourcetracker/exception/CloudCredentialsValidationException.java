package com.resourcetracker.exception;

import java.io.IOException;

/** Represents */
public class CloudCredentialsValidationException extends IOException {
  public CloudCredentialsValidationException() {
    super("Given cloud credentials are not valid!");
  }
}
