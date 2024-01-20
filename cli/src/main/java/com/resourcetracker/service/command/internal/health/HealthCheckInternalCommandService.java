package com.resourcetracker.service.command.internal.health;

import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.HealthCheckResult;
import com.resourcetracker.model.HealthCheckStatus;
import com.resourcetracker.service.client.command.HealthCheckClientCommandService;
import com.resourcetracker.service.command.ICommand;
import com.resourcetracker.service.visualization.state.VisualizationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckInternalCommandService implements ICommand {
  @Autowired private HealthCheckClientCommandService healthCheckClientCommandService;

  @Autowired private VisualizationState visualizationState;

  /**
   * @see ICommand
   */
  @Override
  public void process() throws ApiServerException {
    visualizationState.getLabel().pushNext();

    HealthCheckResult healthCheckResult = healthCheckClientCommandService.process(null);

    if (healthCheckResult.getStatus() == HealthCheckStatus.DOWN) {
      throw new ApiServerException(
          new ApiServerNotAvailableException(healthCheckResult.getChecks().toString())
              .getMessage());
    }
  }
}
