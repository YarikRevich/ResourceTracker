package com.resourcetracker.service.vendor;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.resourcetracker.converter.AgentContextToJsonConverter;
import com.resourcetracker.converter.DeploymentRequestsToAgentContextConverter;
import com.resourcetracker.converter.SecretsConverter;
import com.resourcetracker.dto.*;
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

  /**
   * Starts container execution for a certain cloud provider.
   *
   * @param input Terraform deployment output.
   * @param terraformDeploymentApplication given Terraform deployment application.
   * @return public ip for the Kafka container.
   * @throws ContainerStartupFailureException if container startup flow failed to be processed.
   */
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

        AWSDeploymentResultDto awsDeploymentResult =
            awsVendorService.getEcsTaskInitializationDetails(input);

        awsVendorService.waitForEcsTaskReadiness(
            properties.getAwsResourceTrackerCommonFamily(),
            awsDeploymentResult.getEcsCluster().getValue(),
            awsDeploymentResult.getEcsTaskDefinition().getValue(),
            awsCredentialsProvider,
            terraformDeploymentApplication.getCredentials().getRegion(),
            properties.getAwsReadinessPeriod());

        String serviceMachineAddress =
            awsVendorService.getMachineAddress(
                properties.getAwsResourceTrackerCommonFamily(),
                awsDeploymentResult.getEcsCluster().getValue(),
                awsDeploymentResult.getEcsTaskDefinition().getValue(),
                awsCredentialsProvider,
                terraformDeploymentApplication.getCredentials().getRegion());

        String agentContext =
            AgentContextToJsonConverter.convert(
                DeploymentRequestsToAgentContextConverter.convert(
                    terraformDeploymentApplication.getRequests()));

        AWSTaskDefinitionRegistrationDto ecsTaskDefinitionRegistrationDto =
            AWSTaskDefinitionRegistrationDto.of(
                AWSAgentTaskDefinitionRegistrationDto.of(
                    properties.getResourceTrackerAgentImage(),
                    properties.getAwsResourceTrackerAgentName(),
                    agentContext,
                    properties.getResourceTrackerAgentContextAlias(),
                    properties.getGitCommitId()),
                AWSKafkaTaskDefinitionRegistrationDto.of(
                    properties.getResourceTrackerKafkaImage(),
                    properties.getResourceTrackerKafkaImageVersion(),
                    properties.getAwsResourceTrackerKafkaName(),
                    properties.getResourceTrackerKafkaPort(),
                    serviceMachineAddress,
                    properties.getResourceTrackerKafkaHostAlias(),
                    properties.getKafkaTopic(),
                    properties.getResourceTrackerKafkaCreateTopicsAlias(),
                    properties.getKafkaPartitions(),
                    properties.getResourceTrackerKafkaPartitionsAlias()),
                properties.getAwsResourceTrackerCommonFamily(),
                awsDeploymentResult.getEcsTaskExecutionRole().getValue(),
                properties.getAwsResourceTrackerCommonCPUUnits(),
                properties.getAwsResourceTrackerCommonMemoryUnits());

        String ecsTaskDefinitionsArn =
            awsVendorService.registerEcsTaskDefinitions(
                ecsTaskDefinitionRegistrationDto,
                awsCredentialsProvider,
                terraformDeploymentApplication.getCredentials().getRegion());

        awsVendorService.runEcsTask(
            awsDeploymentResult.getEcsCluster().getValue(),
            ecsTaskDefinitionsArn,
            awsDeploymentResult.getResourceTrackerMainSubnetId().getValue(),
            awsDeploymentResult.getResourceTrackerSecurityGroup().getValue(),
            awsCredentialsProvider,
            terraformDeploymentApplication.getCredentials().getRegion());

        awsVendorService.waitForEcsTaskReadiness(
            properties.getAwsResourceTrackerCommonFamily(),
            awsDeploymentResult.getEcsCluster().getValue(),
            ecsTaskDefinitionsArn,
            awsCredentialsProvider,
            terraformDeploymentApplication.getCredentials().getRegion(),
            properties.getAwsReadinessPeriod());

        //        yield awsVendorService.getMachineAddress(
        //            properties.getAwsResourceTrackerKafkaName(),
        //            awsDeploymentResult.getEcsCluster().getValue(),
        //            awsCredentialsProvider,
        //            terraformDeploymentApplication.getCredentials().getRegion());
        yield "0.0.0.0";
      }
    };
  }

  /**
   * Stops container execution for a certain cloud provider.
   *
   * @param terraformDestructionApplication given Terraform destruction application.
   */
  public void stopContainerExecution(
      TerraformDestructionApplication terraformDestructionApplication) {
    switch (terraformDestructionApplication.getProvider()) {
      case AWS -> {
        AWSSecretsDto secretsDto =
            AWSSecretsDto.of(
                terraformDestructionApplication.getCredentials().getSecrets().getAccessKey(),
                terraformDestructionApplication.getCredentials().getSecrets().getSecretKey());

        AWSCredentialsProvider awsCredentialsProvider =
            AWSVendorService.getAWSCredentialsProvider(secretsDto);

        awsVendorService.deregisterEcsTaskDefinitions(
            properties.getAwsResourceTrackerCommonFamily(),
            awsCredentialsProvider,
            terraformDestructionApplication.getCredentials().getRegion());

        //        awsVendorService.removeEcsExecutionRole(
        //            properties.getAwsResourceTrackerExecutionRole(),
        //            awsCredentialsProvider,
        //            terraformDestructionApplication.getCredentials().getRegion());
      }
    }
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
