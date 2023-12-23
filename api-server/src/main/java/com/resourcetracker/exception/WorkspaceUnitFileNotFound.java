package com.resourcetracker.exception;

import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.Formatter;

public class WorkspaceUnitFileNotFound extends NoSuchFileException {
  public WorkspaceUnitFileNotFound() {
    this("");
  }

  public WorkspaceUnitFileNotFound(Object... message) {
    super(
        new Formatter()
            .format("Workspace unit is not found: %s", Arrays.stream(message).toArray())
            .toString());
  }
}
