package com.resourcetracker.resource;

import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;

public class TerraformResource implements TerraformResourceApi {

    /**
     * @param terraformDeploymentApplication
     */
    @Override
    public void v1TerraformApplyPost(TerraformDeploymentApplication terraformDeploymentApplication) {

    }

    /**
     * @param terraformDestructionApplication
     */
    @Override
    public void v1TerraformDestroyPost(TerraformDestructionApplication terraformDestructionApplication) {

    }
}
