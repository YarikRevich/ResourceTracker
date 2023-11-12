package com.resourcetracker.resource;

import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.terraform.TerraformService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class TerraformResource implements TerraformResourceApi {
    @Inject
    TerraformService terraformService;

    @Override
    public void v1TerraformApplyPost(TerraformDeploymentApplication terraformDeploymentApplication) {
//        terraformService.apply(terraformDeploymentApplication);
    }

    @Override
    public void v1TerraformDestroyPost(TerraformDestructionApplication terraformDestructionApplication) {
//        terraformService.destroy(terraformDestructionApplication);
    }
}
