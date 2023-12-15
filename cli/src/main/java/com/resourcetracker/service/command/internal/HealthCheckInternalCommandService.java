package com.resourcetracker.service.command.internal;

import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.service.client.command.HealthCheckClientCommandService;
import com.resourcetracker.service.command.ICommand;
import com.resourcetracker.service.command.external.StopExternalCommandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckInternalCommandService implements ICommand {
    private static final Logger logger = LogManager.getLogger(HealthCheckInternalCommandService.class);

    @Autowired
    private HealthCheckClientCommandService healthCheckClientCommandService;

    /**
     * @see ICommand
     */
    @Override
    public void process() throws ApiServerNotAvailableException {
        healthCheckClientCommandService.process();
    }
}
