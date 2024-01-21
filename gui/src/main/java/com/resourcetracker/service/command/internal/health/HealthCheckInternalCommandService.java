package com.resourcetracker.service.command.internal.health;

import com.resourcetracker.dto.HealthCheckInternalCommandResultDto;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.HealthCheckResult;
import com.resourcetracker.model.HealthCheckStatus;
import com.resourcetracker.service.client.command.HealthCheckClientCommandService;
import com.resourcetracker.service.command.common.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckInternalCommandService
    implements ICommand<HealthCheckInternalCommandResultDto> {
  @Autowired private HealthCheckClientCommandService healthCheckClientCommandService;

  /**
   * @see ICommand
   */
  @Override
  public HealthCheckInternalCommandResultDto process() {
    HealthCheckResult healthCheckResult;
    try {
      healthCheckResult = healthCheckClientCommandService.process(null);
    } catch (ApiServerException e) {
      return HealthCheckInternalCommandResultDto.of(false, e.getMessage());
    }

    if (healthCheckResult.getStatus() == HealthCheckStatus.DOWN) {
      return HealthCheckInternalCommandResultDto.of(
          false,
          new ApiServerException(
                  new ApiServerNotAvailableException(healthCheckResult.getChecks().toString())
                      .getMessage())
              .getMessage());
    }

    return HealthCheckInternalCommandResultDto.of(true, null);
  }
}
