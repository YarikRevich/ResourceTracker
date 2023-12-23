package com.resourcetracker.service.client.command;

import com.resourcetracker.ApiClient;
import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.ApiServerNotAvailableException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDeploymentApplicationResult;
import com.resourcetracker.service.client.IClientCommand;
import com.resourcetracker.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientRequestException;

/** Represents apply client command service. */
@Service
public class ApplyClientCommandService
    implements IClientCommand<
        TerraformDeploymentApplicationResult, TerraformDeploymentApplication> {
  private final TerraformResourceApi terraformResourceApi;

  public ApplyClientCommandService(@Autowired ConfigService configService) {
    ApiClient apiClient =
        new ApiClient().setBasePath(configService.getConfig().getApiServer().getHost());

    this.terraformResourceApi = new TerraformResourceApi(apiClient);
  }

  /**
   * @see IClientCommand
   */
  @Override
  public TerraformDeploymentApplicationResult process(TerraformDeploymentApplication input)
      throws ApiServerException {
    try {
      return terraformResourceApi.v1TerraformApplyPost(input).block();
    } catch (WebClientRequestException e) {
      throw new ApiServerException(new ApiServerNotAvailableException(e.getMessage()).getMessage());
    }
  }
}
