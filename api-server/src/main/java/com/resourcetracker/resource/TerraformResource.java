package com.resourcetracker.resource;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.entity.AWSDeploymentResult;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDeploymentApplicationResult;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.kafka.KafkaService;
import com.resourcetracker.service.terraform.TerraformService;
import com.resourcetracker.service.vendor.aws.AWSService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;

import java.util.Objects;

/**
 * Contains implementation of TerraformResource.
 */
@ApplicationScoped
public class TerraformResource implements TerraformResourceApi {
    @Inject
    PropertiesEntity properties;

    @Inject
    TerraformService terraformService;

    /**
     * Implementation for declared in OpenAPI configuration v1TerraformApplyPost method.
     * @param terraformDeploymentApplication application used by Terraform to perform deployment.
     * @return Terraform deployment result.
     */
    @Override
    @SneakyThrows
    public TerraformDeploymentApplicationResult v1TerraformApplyPost(TerraformDeploymentApplication terraformDeploymentApplication) {
        if (Objects.isNull(terraformDeploymentApplication)){
            throw new BadRequestException();
        }
        TerraformDeploymentApplicationResult terraformDeploymentApplicationResult = new TerraformDeploymentApplicationResult();

        AWSService awsService = new AWSService(AWSService.getAWSCredentialsProvider(
                terraformDeploymentApplication.getCredentials().getAccessKey(),
                terraformDeploymentApplication.getCredentials().getSecretKey()
        ));

        if (!awsService.isS3BucketExist(properties.getRemoteStorageName())){
            awsService.createS3Bucket(properties.getRemoteStorageName());
        }

        String terraformOutput = terraformService.apply(terraformDeploymentApplication);

        AWSDeploymentResult awsDeploymentResult = awsService.getEcsTaskRunDetails(terraformOutput);
        awsService.runEcsTask(awsDeploymentResult);
        String machineAddress = awsService.getMachineAddress(awsDeploymentResult);

        terraformDeploymentApplicationResult.setMachineAddress(machineAddress);

        return terraformDeploymentApplicationResult;
    }

    /**
     * Implementation for declared in OpenAPI configuration v1TerraformDestroyPost method.
     * @param terraformDestructionApplication application used by Terraform to perform destruction.
     */
    @Override
    @SneakyThrows
    public void v1TerraformDestroyPost(TerraformDestructionApplication terraformDestructionApplication) {
        if (Objects.isNull(terraformDestructionApplication)){
            throw new BadRequestException();
        }

        AWSService awsService = new AWSService(AWSService.getAWSCredentialsProvider(
                terraformDestructionApplication.getCredentials().getAccessKey(),
                terraformDestructionApplication.getCredentials().getSecretKey()
        ));

        if (awsService.isS3BucketExist(properties.getRemoteStorageName())){
            awsService.removeS3Bucket(properties.getRemoteStorageName());
        }

        terraformService.destroy(terraformDestructionApplication);
    }
}
