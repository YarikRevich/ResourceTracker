package com.resourcetracker.service.command.internal.health;

import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.model.HealthCheckResult;
import com.resourcetracker.service.client.command.HealthCheckClientCommandService;
import com.resourcetracker.service.command.ICommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckInternalCommandService implements ICommand {
  private static final Logger logger =
      LogManager.getLogger(HealthCheckInternalCommandService.class);

  @Autowired private HealthCheckClientCommandService healthCheckClientCommandService;

  /**
   * @see ICommand
   */
  @Override
  public void process() throws ApiServerException {
    HealthCheckResult healthCheckResult =
            healthCheckClientCommandService.process(null);

    switch (healthCheckResult.getStatus()) {
      case UP -> System.out.println("API Server is up");
      case DOWN -> System.out.println("API Server is down");
    }
  }
}
