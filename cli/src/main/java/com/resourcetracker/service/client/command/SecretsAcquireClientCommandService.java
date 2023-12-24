package com.resourcetracker.service.client.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.ValidationResourceApi;
import com.resourcetracker.dto.ValidationSecretsApplicationDto;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.exception.CloudCredentialsFileNotFoundException;
import com.resourcetracker.model.Provider;
import com.resourcetracker.model.ValidationSecretsApplication;
import com.resourcetracker.model.ValidationSecretsApplicationResult;
import com.resourcetracker.service.client.IClientCommand;
import com.resourcetracker.service.config.ConfigService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/** Represents secrets validation client command service. */
@Service
public class SecretsAcquireClientCommandService
    implements IClientCommand<ValidationSecretsApplicationResult, ValidationSecretsApplicationDto> {
  private final ValidationResourceApi validationResourceApi;

  public SecretsAcquireClientCommandService(@Autowired ConfigService configService) {
    ApiClient apiClient =
        new ApiClient().setBasePath(configService.getConfig().getApiServer().getHost());

    this.validationResourceApi = new ValidationResourceApi(apiClient);
  }

  /**
   * @see IClientCommand
   */
  @Override
  public ValidationSecretsApplicationResult process(ValidationSecretsApplicationDto input)
      throws ApiServerException {
    Path filePath = Paths.get(input.getFilePath());

    if (Files.notExists(filePath)) {
      throw new ApiServerException(new CloudCredentialsFileNotFoundException().getMessage());
    }

    String content;

    try {
      content = Files.readString(filePath);
    } catch (IOException e) {
      throw new ApiServerException(e.getMessage());
    }

    try {
      return validationResourceApi
          .v1SecretsAcquirePost(ValidationSecretsApplication.of(Provider.AWS, content))
          .block();
    } catch (WebClientResponseException e) {
      throw new ApiServerException(e.getResponseBodyAsString());
    } catch (WebClientRequestException e) {
      throw new ApiServerException(new ApiServerNotAvailableException(e.getHeaders()).getMessage());
    }
  }
}
