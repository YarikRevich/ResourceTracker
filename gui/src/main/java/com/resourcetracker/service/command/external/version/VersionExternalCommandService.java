package com.resourcetracker.service.command.external.version;

import com.resourcetracker.dto.VersionExternalCommandResultDto;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.model.ApplicationInfoResult;
import com.resourcetracker.service.client.command.VersionClientCommandService;
import com.resourcetracker.service.command.common.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents version external command service. */
@Service
public class VersionExternalCommandService implements ICommand<VersionExternalCommandResultDto> {
  @Autowired private PropertiesEntity properties;

  @Autowired private VersionClientCommandService versionClientCommandService;

  /**
   * @see ICommand
   */
  public VersionExternalCommandResultDto process() {
    ApplicationInfoResult applicationInfoResult;
    try {
      applicationInfoResult = versionClientCommandService.process(null);
    } catch (ApiServerException e) {
      return VersionExternalCommandResultDto.of(null, false, e.getMessage());
    }

    return VersionExternalCommandResultDto.of(
        applicationInfoResult.getExternalApi().getHash(), true, null);
  }
}
