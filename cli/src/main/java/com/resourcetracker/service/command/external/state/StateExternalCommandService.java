package com.resourcetracker.service.command.external.state;

import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.service.command.ICommand;
import com.resourcetracker.service.command.external.state.provider.aws.AWSStateExternalCommandService;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents state external command service. */
@Service
public class StateExternalCommandService implements ICommand {
  @Autowired private ConfigService configService;

  @Autowired private AWSStateExternalCommandService awsStateExternalCommandService;

  /**
   * @see ICommand
   */
  public void process() throws ApiServerException {
    switch (configService.getConfig().getCloud().getProvider()) {
      case AWS -> awsStateExternalCommandService.process();
    }
  }
}
