package com.resourcetracker.service.client.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.client.common.IClientCommand;
import com.resourcetracker.service.config.ConfigService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/** Represents destroy client command service. */
@Service
public class DestroyClientCommandService
    implements IClientCommand<Void, TerraformDestructionApplication> {
  @Autowired private ConfigService configService;
  private TerraformResourceApi terraformResourceApi;

  /**
   * @see IClientCommand
   */
  @Override
  @PostConstruct
  public void configure() {
    ApiClient apiClient =
        new ApiClient().setBasePath(configService.getConfig().getApiServer().getHost());

    this.terraformResourceApi = new TerraformResourceApi(apiClient);
  }

  /**
   * @see IClientCommand
   */
  public Void process(TerraformDestructionApplication input) throws ApiServerException {
    try {
      return terraformResourceApi.v1TerraformDestroyPost(input).block();
    } catch (WebClientResponseException e) {
      throw new ApiServerException(e.getResponseBodyAsString());
    } catch (WebClientRequestException e) {
      throw new ApiServerException(new ApiServerNotAvailableException(e.getMessage()).getMessage());
    }
  }
}
