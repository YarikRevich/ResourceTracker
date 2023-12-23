package com.resourcetracker.resource;

import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDeploymentApplicationResult;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.terraform.TerraformAdapter;
import com.resourcetracker.service.vendor.VendorFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import java.util.Objects;
import lombok.SneakyThrows;

/** Contains implementation of TerraformResource. */
@ApplicationScoped
public class TerraformResource implements TerraformResourceApi {
  @Inject TerraformAdapter terraformAdapter;

  @Inject VendorFacade vendorFacade;

  /**
   * Implementation for declared in OpenAPI configuration v1TerraformApplyPost method.
   *
   * @param terraformDeploymentApplication application used by Terraform to perform deployment.
   * @return Terraform deployment result.
   */
  @Override
  @SneakyThrows
  public TerraformDeploymentApplicationResult v1TerraformApplyPost(
      TerraformDeploymentApplication terraformDeploymentApplication) {
    if (Objects.isNull(terraformDeploymentApplication)) {
      throw new BadRequestException();
    }

    vendorFacade.initBackendStorage(terraformDeploymentApplication);

    //    String terraformOutput = terraformAdapter.apply(terraformDeploymentApplication);

    //        String machineAddress = vendorFacade.startContainerExecution(
    //                terraformDeploymentApplication.getProvider(),
    //                terraformOutput,
    //                terraformDeploymentApplication.getCredentials());

    return TerraformDeploymentApplicationResult.of("198.123.0.2");
  }

  /**
   * Implementation for declared in OpenAPI configuration v1TerraformDestroyPost method.
   *
   * @param terraformDestructionApplication application used by Terraform to perform destruction.
   */
  @Override
  @SneakyThrows
  public void v1TerraformDestroyPost(
      TerraformDestructionApplication terraformDestructionApplication) {
    if (Objects.isNull(terraformDestructionApplication)) {
      throw new BadRequestException();
    }

    terraformAdapter.destroy(terraformDestructionApplication);

    vendorFacade.destroyBackendStorage(terraformDestructionApplication);
  }
}
