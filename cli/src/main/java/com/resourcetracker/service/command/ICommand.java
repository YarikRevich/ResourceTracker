package com.resourcetracker.service.command;

import com.resourcetracker.exception.ApiServerException;

/** Represents common command interface. */
public interface ICommand {
  /** Processes certain request for an external command. */
  void process() throws ApiServerException;
}
