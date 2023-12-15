package com.resourcetracker.service.client.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.InfoResourceApi;
import com.resourcetracker.model.ApplicationInfoResult;
import com.resourcetracker.service.client.IClientCommand;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** */
@Service
public class VersionClientCommandService implements IClientCommand<ApplicationInfoResult> {
  private static final Logger logger = LogManager.getLogger(VersionClientCommandService.class);

  private final InfoResourceApi infoResourceApi;

  public VersionClientCommandService(@Autowired ConfigService configService) {
    ApiClient apiClient =
        new ApiClient().setBasePath(configService.getConfig().getApiServer().getHost());

    this.infoResourceApi = new InfoResourceApi(apiClient);
  }

  /**
   * @see IClientCommand
   */
  public ApplicationInfoResult process() {
    infoResourceApi
        .v1InfoVersionGet()
        .doOnError(element -> logger.fatal(element.getMessage()))
        .doOnSuccess(
            element -> {
              System.out.println(element.getExternalApi().getVersion());
            })
        .block();
  }
}
