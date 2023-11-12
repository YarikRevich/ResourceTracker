package com.resourcetracker.resource;

import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDeploymentApplicationResult;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.terraform.TerraformService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import org.openapi.quarkus.openapi_yml.api.TerraformResourceApi;

@ApplicationScoped
public class TerraformResource implements TerraformResourceApi {
    @Inject
    TerraformService terraformService;

    @Override
    public TerraformDeploymentApplicationResult v1TerraformApplyPost(TerraformDeploymentApplication terraformDeploymentApplication) {
        new TerraformDeploymentApplicationResult();
        return null;
//        terraformService.apply(terraformDeploymentApplication);
    }

    @Override
    public void v1TerraformDestroyPost(TerraformDestructionApplication terraformDestructionApplication) {
//        terraformService.destroy(terraformDestructionApplication);
    }
}
