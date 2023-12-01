package com.resourcetracker.service.terraform;

import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.terraform.provider.aws.AWSTerraformProviderService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Represents Terraform services to operate multiple
 * vendors to perform provider related operations.
 */
@ApplicationScoped
public class TerraformService {
  @Inject
  AWSTerraformProviderService awsProviderService;

  /**
   * Applies certain provider deployment configuration.
   * @param terraformDeploymentApplication deployment application.
   * @return remote machine address.
   * @throws TerraformException when any deployment operation step failed.
   */
  public String apply(TerraformDeploymentApplication terraformDeploymentApplication) throws TerraformException {
    return switch (terraformDeploymentApplication.getProvider()){
      case AWS -> awsProviderService.apply(terraformDeploymentApplication);
    };
  }

  /**
   * Destroys certain provider deployment configuration.
   * @param terraformDestructionApplication destruction application.
   * @throws TerraformException when any destruction operation step failed.
   */
  public void destroy(TerraformDestructionApplication terraformDestructionApplication) throws TerraformException {
    switch (terraformDestructionApplication.getProvider()){
      case AWS -> awsProviderService.destroy(terraformDestructionApplication);
    }
  }
}
