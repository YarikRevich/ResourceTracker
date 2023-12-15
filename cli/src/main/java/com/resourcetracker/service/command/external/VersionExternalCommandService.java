package com.resourcetracker.service.command.external;

import com.resourcetracker.ApiClient;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.service.client.command.VersionClientCommandService;
import com.resourcetracker.service.command.ICommand;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents */
@Service
public class VersionExternalCommandService implements ICommand {
  private static final Logger logger = LogManager.getLogger(VersionExternalCommandService.class);

  @Autowired
  private ConfigService configService;

  @Autowired
  private VersionClientCommandService versionClientCommandService;

  /**
   * @see ICommand
   */
  public void process() throws ApiServerNotAvailableException {
    versionClientCommandService.process();
  }
}
