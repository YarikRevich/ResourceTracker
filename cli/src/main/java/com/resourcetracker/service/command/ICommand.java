package com.resourcetracker.service.command;

import com.resourcetracker.exception.ApiServerNotAvailableException;

/**
 * Represents common command interface.
 */
public interface ICommand {
    /**
     * Processes certain request for an external command.
     */
    void process() throws ApiServerNotAvailableException;
}
