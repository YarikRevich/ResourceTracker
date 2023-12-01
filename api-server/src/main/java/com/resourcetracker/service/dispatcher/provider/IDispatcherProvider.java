package com.resourcetracker.service.dispatcher.provider;

import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;

/**
 * Interface for Dispatcher service to execute cloud provider
 * deployment related actions.
 */
public interface IDispatcherProvider {
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
