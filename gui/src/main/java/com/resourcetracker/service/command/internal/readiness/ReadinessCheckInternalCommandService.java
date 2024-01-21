package com.resourcetracker.service.command.internal.readiness;

import com.resourcetracker.dto.ReadinessCheckInternalCommandResultDto;
import com.resourcetracker.service.command.common.ICommand;
import com.resourcetracker.service.command.internal.readiness.provider.aws.AWSReadinessCheckInternalCommandService;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents readiness check internal command service. */
@Service
public class ReadinessCheckInternalCommandService
    implements ICommand<ReadinessCheckInternalCommandResultDto> {
  @Autowired private ConfigService configService;

  @Autowired
  private AWSReadinessCheckInternalCommandService awsReadinessCheckInternalCommandService;

  /**
   * @see ICommand
   */
  @Override
  public ReadinessCheckInternalCommandResultDto process() {
    return switch (configService.getConfig().getCloud().getProvider()) {
      case AWS -> awsReadinessCheckInternalCommandService.process();
    };
  }
}
