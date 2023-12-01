package com.resourcetracker.service.dispatcher.provider.aws;

import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.dispatcher.provider.IDispatcherProvider;

public class AWSDispatcherProviderService implements IDispatcherProvider {
    /**
     * @see IDispatcherProvider
     */
    @Override
    public String apply(TerraformDeploymentApplication terraformDeploymentApplication) throws TerraformException {
        return null;
    }

    /**
     * @see IDispatcherProvider
     */
    @Override
    public void destroy(TerraformDestructionApplication terraformDestructionApplication) throws TerraformException {

    }
}
