package com.resourcetracker.service.vendor;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.auditmanager.model.AWSService;
import com.resourcetracker.dto.AWSSecretsDto;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.model.Provider;
import com.resourcetracker.service.vendor.aws.AWSVendorService;
import com.resourcetracker.model.TerraformDeploymentApplicationCredentials;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Provides high-level access to cloud vendor operations.
 */
@ApplicationScoped
public class VendorFacade {
    @Inject
    PropertiesEntity properties;

    @Inject
    AWSVendorService awsVendorService;

    /**
     * Initializes backend Terraform state storage in the given provider.
     */
    public void initBackendStorage(Provider provider){
        switch (provider) {
            case AWS -> {
                if (!awsVendorService.isS3BucketExist(properties.getRemoteStorageName())){
                    awsVendorService.createS3Bucket(properties.getRemoteStorageName());
                }
            }
        }
    }

    /**
     * Destroys backend Terraform state storage in the given provider.
     */
    public <T> void destroyBackendStorage(Provider provider, T secrets){

        //
//        AWSVendorService awsVendorService = new AWSVendorService(AWSVendorService.getAWSCredentialsProvider(
//                terraformDestructionApplication.getCredentials().getAccessKey(),
//                terraformDestructionApplication.getCredentials().getSecretKey()
//        ));
        switch (provider){
            case AWS -> {
                AWSVendorService.getAWSCredentialsProvider((AWSSecretsDto)secrets);

                if (awsVendorService.isS3BucketExist(properties.getRemoteStorageName())){
                    awsVendorService.removeS3Bucket(properties.getRemoteStorageName());
                }
            }
        }
    }

    /**
     * Checks if the given provider credentials are valid.
     * @return result of provider credentials validation.
     */
    public boolean isCredentialsValid(Provider provider, AWSCredentialsProvider credentials) {
        return switch (provider){
            case AWS -> awsVendorService.isCallerValid(credentials);
        };
    }
}
