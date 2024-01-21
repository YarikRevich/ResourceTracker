package com.resourcetracker.service.command.external.state;

import com.resourcetracker.dto.StateExternalCommandResultDto;
import com.resourcetracker.service.command.common.ICommand;
import com.resourcetracker.service.command.external.state.provider.aws.AWSStateExternalCommandService;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents state external command service. */
@Service
public class StateExternalCommandService implements ICommand<StateExternalCommandResultDto> {
  @Autowired private ConfigService configService;

  @Autowired private AWSStateExternalCommandService awsStateExternalCommandService;

  /**
   * @see ICommand
   */
  public StateExternalCommandResultDto process() {
    return switch (configService.getConfig().getCloud().getProvider()) {
      case AWS -> awsStateExternalCommandService.process();
    };
  }
}
