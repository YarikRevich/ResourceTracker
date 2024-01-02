package com.resourcetracker.service.vendor;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.resourcetracker.converter.SecretsConverter;
import com.resourcetracker.dto.AWSDeploymentResultDto;
import com.resourcetracker.dto.AWSSecretsDto;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.ContainerStartupFailureException;
import com.resourcetracker.exception.SecretsConversionException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.model.ValidationSecretsApplication;
import com.resourcetracker.model.ValidationSecretsApplicationResult;
import com.resourcetracker.model.ValidationSecretsApplicationResultSecrets;
import com.resourcetracker.service.vendor.aws.AWSVendorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/** Provides high-level access to cloud vendor operations. */
@ApplicationScoped
public class VendorFacade {

  @Inject PropertiesEntity properties;

  @Inject AWSVendorService awsVendorService;

  /** Initializes backend Terraform state storage in the given provider. */
  public void initBackendStorage(TerraformDeploymentApplication terraformDeploymentApplication) {
    switch (terraformDeploymentApplication.getProvider()) {
      case AWS -> {
        AWSSecretsDto secretsDto =
            AWSSecretsDto.of(
                terraformDeploymentApplication.getCredentials().getSecrets().getAccessKey(),
                terraformDeploymentApplication.getCredentials().getSecrets().getSecretKey());

        AWSCredentialsProvider awsCredentialsProvider =
            AWSVendorService.getAWSCredentialsProvider(secretsDto);

        if (!awsVendorService.isS3BucketExist(
            properties.getRemoteStorageName(),
            awsCredentialsProvider,
            terraformDeploymentApplication.getCredentials().getRegion())) {
          awsVendorService.createS3Bucket(
              properties.getRemoteStorageName(),
              awsCredentialsProvider,
              terraformDeploymentApplication.getCredentials().getRegion());
        }
      }
    }
  }

  /** Destroys backend Terraform state storage in the given provider. */
  public void destroyBackendStorage(
      TerraformDestructionApplication terraformDestructionApplication) {
    switch (terraformDestructionApplication.getProvider()) {
      case AWS -> {
        AWSSecretsDto secretsDto =
            AWSSecretsDto.of(
                terraformDestructionApplication.getCredentials().getSecrets().getAccessKey(),
                terraformDestructionApplication.getCredentials().getSecrets().getSecretKey());

        AWSCredentialsProvider awsCredentialsProvider =
            AWSVendorService.getAWSCredentialsProvider(secretsDto);

        if (awsVendorService.isS3BucketExist(
            properties.getRemoteStorageName(),
            awsCredentialsProvider,
            terraformDestructionApplication.getCredentials().getRegion())) {
          awsVendorService.removeS3Bucket(
              properties.getRemoteStorageName(),
              awsCredentialsProvider,
              terraformDestructionApplication.getCredentials().getRegion());
        }
      }
    }
  }

  /** Starts container execution of a certain cloud provider. */
  public String startContainerExecution(
      String input, TerraformDeploymentApplication terraformDeploymentApplication)
      throws ContainerStartupFailureException {
    return switch (terraformDeploymentApplication.getProvider()) {
      case AWS -> {
        AWSSecretsDto secretsDto =
            AWSSecretsDto.of(
                terraformDeploymentApplication.getCredentials().getSecrets().getAccessKey(),
                terraformDeploymentApplication.getCredentials().getSecrets().getSecretKey());

        AWSCredentialsProvider awsCredentialsProvider =
            AWSVendorService.getAWSCredentialsProvider(secretsDto);

        AWSDeploymentResultDto awsDeploymentResult = awsVendorService.getEcsTaskRunDetails(input);
        //        try {
        //          awsVendorService.runEcsTask(
        //              awsDeploymentResult,
        //              awsCredentialsProvider,
        //              terraformDeploymentApplication.getCredentials().getRegion());
        //        } catch (AWSRunTaskFailureException e) {
        //          throw new ContainerStartupFailureException(e.getMessage());
        //        }

        //        awsVendorService.runEcsTask();

        yield awsVendorService.getMachineAddress(
            awsDeploymentResult.getEcsCluster().getValue(),
            awsCredentialsProvider,
            terraformDeploymentApplication.getCredentials().getRegion());
      }
    };
  }

  /**
   * Checks if the given provider credentials are valid.
   *
   * @return result of provider credentials validation.
   */
  public ValidationSecretsApplicationResult isCredentialsValid(
      ValidationSecretsApplication validationSecretsApplication) throws SecretsConversionException {
    return switch (validationSecretsApplication.getProvider()) {
      case AWS -> {
        AWSSecretsDto secrets =
            SecretsConverter.convert(AWSSecretsDto.class, validationSecretsApplication.getFile());

        AWSCredentialsProvider awsCredentialsProvider =
            AWSVendorService.getAWSCredentialsProvider(secrets);

        yield ValidationSecretsApplicationResult.of(
            awsVendorService.isCallerValid(
                awsCredentialsProvider, properties.getAwsDefaultRegion()),
            ValidationSecretsApplicationResultSecrets.of(
                secrets.getAccessKey(), secrets.getSecretKey()));
      }
    };
  }
}
