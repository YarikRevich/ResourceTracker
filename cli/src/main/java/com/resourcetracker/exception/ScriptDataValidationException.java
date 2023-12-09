package com.resourcetracker.exception;

import java.io.IOException;

/** Represents exception, when given file and */
public class ScriptDataValidationException extends IOException {
  public ScriptDataValidationException() {
    super("Given explicit script or file with script is not present or incorrect");
  }
}
