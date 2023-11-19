package com.resourcetracker.resource;

import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDeploymentApplicationResult;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.terraform.TerraformService;
import com.resourcetracker.service.vendor.aws.AWSService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class TerraformResource extends TerraformResourceApi {
    @Inject
    TerraformService terraformService;

    @Inject
    AWSService awsService;

    @Override
    public Response v1TerraformApplyPost(TerraformDeploymentApplication terraformDeploymentApplication) {
//        new TerraformDeploymentApplicationResult();
        // terraformService.apply(terraformDeploymentApplication)

//        awsService.getEcsTaskRunDetails();
//
//        awsService.getEcsTaskRunDetails();
        return null;
//        terraformService.apply(terraformDeploymentApplication);
    }

    @Override
    public Response v1TerraformDestroyPost(TerraformDestructionApplication terraformDestructionApplication) {
        return null;
//        terraformService.destroy(terraformDestructionApplication);
    }
}
