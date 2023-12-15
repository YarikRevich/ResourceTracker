package com.resourcetracker.service.client;

import com.resourcetracker.exception.ApiServerNotAvailableException;

/**
 * Represents external resource command interface.
 * @param <T> type of the command response.
 */
public interface IClientCommand<T> {
    /**
     * Processes certain request for an external command.
     * @return command response.
     */
    T process() throws ApiServerNotAvailableException;
}
