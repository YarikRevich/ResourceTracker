package com.resourcetracker.service.command.external.stop;

import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.model.*;
import com.resourcetracker.service.command.common.ICommand;
import com.resourcetracker.service.command.external.stop.provider.aws.AWSStopExternalCommandService;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents stop external command service. */
@Service
public class StopExternalCommandService implements ICommand {
  @Autowired private ConfigService configService;

  @Autowired private AWSStopExternalCommandService stopExternalCommandService;

  /**
   * @see ICommand
   */
  public void process() throws ApiServerException {
    switch (configService.getConfig().getCloud().getProvider()) {
      case AWS -> stopExternalCommandService.process();
    }
  }
}
