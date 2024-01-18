package com.resourcetracker.service.command.internal.readiness;

import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.model.*;
import com.resourcetracker.service.command.ICommand;
import com.resourcetracker.service.command.internal.readiness.provider.aws.AWSReadinessCheckInternalCommandService;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReadinessCheckInternalCommandService implements ICommand {
  @Autowired private ConfigService configService;

  @Autowired
  private AWSReadinessCheckInternalCommandService awsReadinessCheckInternalCommandService;

  /**
   * @see ICommand
   */
  @Override
  public void process() throws ApiServerException {
    switch (configService.getConfig().getCloud().getProvider()) {
      case AWS -> awsReadinessCheckInternalCommandService.process();
    }
  }
}
