package com.resourcetracker.service.terraform.provider;

import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;

/**
 * Interface for Terraform service to execute cloud providers.
 */
public interface ITerraformProvider {
    /**
     * Applies certain provider deployment configuration.
     * @param terraformDeploymentApplication deployment application.
     * @return remote machine address.
     * @throws TerraformException when any deployment operation step failed.
     */
    String apply(TerraformDeploymentApplication terraformDeploymentApplication) throws TerraformException;

    /**
     * Destroys certain provider deployment configuration.
     * @param terraformDestructionApplication destruction application.
     * @throws TerraformException when any destruction operation step failed.
     */
    void destroy(TerraformDestructionApplication terraformDestructionApplication) throws TerraformException;
}
