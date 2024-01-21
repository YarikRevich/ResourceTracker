package com.resourcetracker.service.command.external.stop;

import com.resourcetracker.dto.StopExternalCommandResultDto;
import com.resourcetracker.service.command.common.ICommand;
import com.resourcetracker.service.command.external.stop.provider.aws.AWSStopExternalCommandService;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents stop external command service. */
@Service
public class StopExternalCommandService implements ICommand<StopExternalCommandResultDto> {
  @Autowired private ConfigService configService;

  @Autowired private AWSStopExternalCommandService stopExternalCommandService;

  /**
   * @see ICommand
   */
  public StopExternalCommandResultDto process() {
    return switch (configService.getConfig().getCloud().getProvider()) {
      case AWS -> stopExternalCommandService.process();
    };
  }
}
