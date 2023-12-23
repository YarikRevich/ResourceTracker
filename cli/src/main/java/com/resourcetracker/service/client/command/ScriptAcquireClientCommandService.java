package com.resourcetracker.service.client.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.ValidationResourceApi;
import com.resourcetracker.dto.ValidationScriptApplicationDto;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.ValidationScriptApplication;
import com.resourcetracker.model.ValidationScriptApplicationResult;
import com.resourcetracker.service.client.IClientCommand;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;

/** Represents script validation client command service. */
@Service
public class ScriptAcquireClientCommandService
    implements IClientCommand<ValidationScriptApplicationResult, ValidationScriptApplicationDto> {
  private final ValidationResourceApi validationResourceApi;

  public ScriptAcquireClientCommandService(@Autowired ConfigService configService) {
    ApiClient apiClient =
        new ApiClient().setBasePath(configService.getConfig().getApiServer().getHost());

    this.validationResourceApi = new ValidationResourceApi(apiClient);
  }

  /**
   * @see IClientCommand
   */
  @Override
  public ValidationScriptApplicationResult process(ValidationScriptApplicationDto input)
      throws ApiServerException {
    try {
      return validationResourceApi
          .v1ScriptAcquirePost(ValidationScriptApplication.of(input.getFileContent()))
          .block();
    } catch (WebClientRequestException e) {
      throw new ApiServerException(new ApiServerNotAvailableException(e.getMessage()));
    }
  }
}
