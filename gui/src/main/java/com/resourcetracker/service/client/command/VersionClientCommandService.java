package com.resourcetracker.service.client.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.InfoResourceApi;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.ApplicationInfoResult;
import com.resourcetracker.service.client.common.IClientCommand;
import com.resourcetracker.service.config.ConfigService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/** Represents version information client command service. */
@Service
public class VersionClientCommandService implements IClientCommand<ApplicationInfoResult, Void> {
  @Autowired private ConfigService configService;

  private InfoResourceApi infoResourceApi;

  /**
   * @see IClientCommand
   */
  @Override
  @PostConstruct
  public void configure() {
    ApiClient apiClient =
        new ApiClient().setBasePath(configService.getConfig().getApiServer().getHost());

    this.infoResourceApi = new InfoResourceApi(apiClient);
  }

  /**
   * @see IClientCommand
   */
  public ApplicationInfoResult process(Void input) throws ApiServerException {
    try {
      return infoResourceApi.v1InfoVersionGet().block();
    } catch (WebClientResponseException e) {
      throw new ApiServerException(e.getResponseBodyAsString());
    } catch (WebClientRequestException e) {
      throw new ApiServerException(new ApiServerNotAvailableException(e.getMessage()).getMessage());
    }
  }
}
