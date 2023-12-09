package com.resourcetracker.service.vendor;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.resourcetracker.dto.AWSSecretsDto;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.ContainerStartupFailureException;
import com.resourcetracker.model.Provider;
import com.resourcetracker.service.vendor.aws.AWSVendorService;
// import com.resourcetracker.model.TerraformDeploymentApplicationCredentials;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/** Provides high-level access to cloud vendor operations. */
@ApplicationScoped
public class VendorFacade {
  @Inject PropertiesEntity properties;

  @Inject AWSVendorService awsVendorService;

  /** Initializes backend Terraform state storage in the given provider. */
  public <T> void initBackendStorage(Provider provider, T secrets) {
    switch (provider) {
      case AWS -> {
        AWSCredentialsProvider awsCredentialsProvider =
            AWSVendorService.getAWSCredentialsProvider((AWSSecretsDto) secrets);

        if (!awsVendorService.isS3BucketExist(
            properties.getRemoteStorageName(), awsCredentialsProvider)) {
          awsVendorService.createS3Bucket(
              properties.getRemoteStorageName(), awsCredentialsProvider);
        }
      }
    }
  }

  /** Destroys backend Terraform state storage in the given provider. */
  public <T> void destroyBackendStorage(Provider provider, T secrets) {
    switch (provider) {
      case AWS -> {
        AWSCredentialsProvider awsCredentialsProvider =
            AWSVendorService.getAWSCredentialsProvider((AWSSecretsDto) secrets);

        if (awsVendorService.isS3BucketExist(
            properties.getRemoteStorageName(), awsCredentialsProvider)) {
          awsVendorService.removeS3Bucket(
              properties.getRemoteStorageName(), awsCredentialsProvider);
        }
      }
    }
  }

  /** Starts container execution of a certain cloud provider. */
  public <T> String startContainerExecution(
      Provider provider, String input, com.resourcetracker.model.CredentialsFields credentials)
      throws ContainerStartupFailureException {
    return switch (provider) {
      case AWS -> {
        //                AWSCredentialsProvider awsCredentialsProvider = AWSVendorService
        //                        .getAWSCredentialsProvider((AWSSecretsDto)secrets);
        //
        //                AWSDeploymentResult awsDeploymentResult =
        // awsVendorService.getEcsTaskRunDetails(input);
        //
        //                try {
        //                    awsVendorService.runEcsTask(awsDeploymentResult,
        // awsCredentialsProvider);
        //                } catch (AWSRunTaskFailureException e) {
        //                    throw new ContainerStartupFailureException(e.getMessage());
        //                }
        //
        //                yield awsVendorService.getMachineAddress(
        //                        awsDeploymentResult
        //                                .getEcsCluster()
        //                                .getValue(),
        //                        awsCredentialsProvider);
        yield "";
      }
    };
  }

  /**
   * Checks if the given provider credentials are valid.
   *
   * @return result of provider credentials validation.
   */
  public <T> boolean isCredentialsValid(Provider provider, T secrets) {
    return switch (provider) {
      case AWS -> {
        AWSCredentialsProvider awsCredentialsProvider =
            AWSVendorService.getAWSCredentialsProvider((AWSSecretsDto) secrets);

        yield awsVendorService.isCallerValid(awsCredentialsProvider);
      }
    };
  }
}
