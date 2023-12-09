package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Formatter;

public class SecretsConversionException extends IOException {
  public SecretsConversionException(Object... message) {
    super(new Formatter().format("Given secrets are invalid", message).toString());
  }
}
