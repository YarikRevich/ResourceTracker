package com.resourcetracker.exception;

import java.io.IOException;
import java.util.Formatter;

public class ApplicationFontFileNotFoundException extends IOException {
  public ApplicationFontFileNotFoundException(Object... message) {
    super(
        new Formatter()
            .format("Application font file at the given location is not available", message)
            .toString());
  }
}
