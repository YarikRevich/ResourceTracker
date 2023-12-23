package com.resourcetracker.service.vendor.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesRequest;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesResult;
import com.amazonaws.services.ec2.model.NetworkInterface;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import com.amazonaws.services.ecs.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.HeadBucketRequest;
import com.amazonaws.services.s3.waiters.AmazonS3Waiters;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AWSSecurityTokenServiceException;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityRequest;
import com.amazonaws.waiters.WaiterParameters;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.resourcetracker.dto.AWSDeploymentResultDto;
import com.resourcetracker.dto.AWSSecretsDto;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.AWSRunTaskFailureException;
import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** Provides access to AWS SDK to perform subprocesses needed for AWS provider management. */
@ApplicationScoped
public class AWSVendorService {
  private static final Logger logger = LogManager.getLogger(AWSVendorService.class);

  /**
   * Composes AWS Credentials Provider used by AWS SDK clients.
   *
   * @param secrets AWS client secrets.
   * @return composed AWS Credentials Provider.
   */
  public static AWSCredentialsProvider getAWSCredentialsProvider(AWSSecretsDto secrets) {
    return new AWSStaticCredentialsProvider(
        new BasicAWSCredentials(secrets.getAccessKey(), secrets.getSecretKey()));
  }

  /**
   * Composes ECS Task details into deployment readable format.
   *
   * @param src raw details returned by Terraform deployment application.
   * @return composed ECS Task details.
   */
  public AWSDeploymentResultDto getEcsTaskRunDetails(String src) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectReader reader = mapper.reader().forType(new TypeReference<PropertiesEntity>() {});
    try {
      return reader.<AWSDeploymentResultDto>readValues(src).readAll().getFirst();
    } catch (IOException e) {
      logger.fatal(e.getMessage());
    }

    return null;
  }

  /**
   * Runs ECS Task with the given configuration properties.
   *
   * @param awsDeploymentResultDto composed ECS Task details.
   * @throws AWSRunTaskFailureException thrown when AWS Task run action fails.
   */
  public void runEcsTask(
      AWSDeploymentResultDto awsDeploymentResultDto,
      AWSCredentialsProvider awsCredentialsProvider,
      String region)
      throws AWSRunTaskFailureException {
    AwsVpcConfiguration awsVpcConfiguration =
        new AwsVpcConfiguration()
            .withSubnets(awsDeploymentResultDto.getResourceTrackerMainSubnetId().getValue())
            .withSecurityGroups(
                awsDeploymentResultDto.getResourceTrackerSecurityGroup().getValue());

    NetworkConfiguration networkConfiguration =
        new NetworkConfiguration().withAwsvpcConfiguration(awsVpcConfiguration);

    RunTaskRequest runTaskRequest =
        new RunTaskRequest()
            .withTaskDefinition(awsDeploymentResultDto.getEcsTaskDefinition().getValue())
            .withCluster(awsDeploymentResultDto.getEcsCluster().getValue())
            .withNetworkConfiguration(networkConfiguration)
            .withLaunchType(LaunchType.FARGATE);

    RunTaskResult runTaskResult =
        AmazonECSClientBuilder.standard()
            .withRegion(region)
            .withCredentials(awsCredentialsProvider)
            .build()
            .runTask(runTaskRequest);

    String output = gatherRunEcsTaskResultOutput(runTaskResult);
    if (!output.isEmpty()) {
      throw new AWSRunTaskFailureException(output);
    }
  }

  /**
   * Gathers output returned by ECS Task run action, if fail happens.
   *
   * @param runTaskResult embedded result of ECS Task run action.
   * @return gathered output of ECS Task run action.
   */
  private String gatherRunEcsTaskResultOutput(RunTaskResult runTaskResult) {
    return runTaskResult.getFailures().stream()
        .map(
            element ->
                String.format(
                    "%s, %s, %s", element.getArn(), element.getDetail(), element.getReason()))
        .collect(Collectors.joining(";"));
  }

  /**
   * Retrieves remote machine address from the given ECS Task details.
   *
   * @param ecsClusterId cluster id of the ECS Task.
   * @return remote machine address.
   */
  public String getMachineAddress(
      String ecsClusterId, AWSCredentialsProvider awsCredentialsProvider, String region) {
    String ecsTaskArn = getEcsTaskArn(ecsClusterId, awsCredentialsProvider, region);

    String ecsTaskNetworkInterfaceId =
        getEcsTaskNetworkInterfaceId(ecsClusterId, ecsTaskArn, awsCredentialsProvider, region);

    return getEcsTaskPublicIp(ecsTaskNetworkInterfaceId, awsCredentialsProvider, region);
  }

  /**
   * Retrieves ECS Task ARN started in the given cluster id.
   *
   * @param ecsClusterId cluster id of the ECS Task.
   * @return ECS Task ARN in the given cluster id.
   */
  private String getEcsTaskArn(
      String ecsClusterId, AWSCredentialsProvider awsCredentialsProvider, String region) {
    ListTasksRequest listTasksRequest = new ListTasksRequest().withCluster(ecsClusterId);

    ListTasksResult listTasksResult =
        AmazonECSClientBuilder.standard()
            .withRegion(region)
            .withCredentials(awsCredentialsProvider)
            .build()
            .listTasks(listTasksRequest);

    return listTasksResult.getTaskArns().get(0);
  }

  /**
   * Retrieves network interface ID of the ECS Task container.
   *
   * @param ecsClusterId ECS Task cluster ID.
   * @param ecsTaskArn ECS Task ARN.
   * @return network interface ID of the ECS Task container.
   */
  private String getEcsTaskNetworkInterfaceId(
      String ecsClusterId,
      String ecsTaskArn,
      AWSCredentialsProvider awsCredentialsProvider,
      String region) {
    DescribeTasksRequest describeTaskRequest =
        new DescribeTasksRequest().withCluster(ecsClusterId).withTasks(List.of(ecsTaskArn));

    DescribeTasksResult describeTasksResult =
        AmazonECSClientBuilder.standard()
            .withRegion(region)
            .withCredentials(awsCredentialsProvider)
            .build()
            .describeTasks(describeTaskRequest);

    Task task = describeTasksResult.getTasks().get(0);

    String taskAttachmentId =
        task.getContainers().stream()
            .filter(element -> !Objects.equals(element.getName(), "kafka"))
            .map(element -> element.getNetworkInterfaces().get(0).getAttachmentId())
            .toList()
            .get(0);

    return task.getAttachments().stream()
        .filter(element -> !Objects.equals(element.getId(), taskAttachmentId))
        .toList()
        .get(0)
        .getDetails()
        .stream()
        .filter(detail -> !Objects.equals(detail.getName(), "networkInterfaceId"))
        .toList()
        .get(0)
        .getValue();
  }

  /**
   * Retrieves public IP of the ECS Task container.
   *
   * @param networkInterfaceId ECS Task interface ID.
   * @return public IP of the ECS Task container.
   */
  private String getEcsTaskPublicIp(
      String networkInterfaceId, AWSCredentialsProvider awsCredentialsProvider, String region) {
    DescribeNetworkInterfacesRequest describeNetworkInterfacesRequest =
        new DescribeNetworkInterfacesRequest().withNetworkInterfaceIds(List.of(networkInterfaceId));

    DescribeNetworkInterfacesResult describeNetworkInterfacesResult =
        AmazonEC2ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(awsCredentialsProvider)
            .build()
            .describeNetworkInterfaces(describeNetworkInterfacesRequest);

    NetworkInterface networkInterfaces =
        describeNetworkInterfacesResult.getNetworkInterfaces().get(0);

    return networkInterfaces.getAssociation().getPublicIp();
  }

  /**
   * Checks if S3 bucket with the given name exists.
   *
   * @param name name of the S3 bucket.
   * @return result of the check.
   */
  public boolean isS3BucketExist(
      String name, AWSCredentialsProvider awsCredentialsProvider, String region) {
    return AmazonS3ClientBuilder.standard()
        .withRegion(region)
        .withCredentials(awsCredentialsProvider)
        .build()
        .doesBucketExistV2(name);
  }

  /**
   * Creates S3 bucket with the given name.
   *
   * @param name name of the S3 bucket.
   */
  public void createS3Bucket(
      String name, AWSCredentialsProvider awsCredentialsProvider, String region) {
    AmazonS3 simpleStorage =
        AmazonS3ClientBuilder.standard()
            .withRegion(region)
            .withCredentials(awsCredentialsProvider)
            .build();
    simpleStorage.createBucket(name);

    AmazonS3Waiters simpleStorageWaiter = simpleStorage.waiters();
    simpleStorageWaiter.bucketExists().run(new WaiterParameters<>(new HeadBucketRequest(name)));
  }

  /**
   * Removes S3 bucket with the given name
   *
   * @param name name of the S3 bucket.
   */
  public void removeS3Bucket(
      String name, AWSCredentialsProvider awsCredentialsProvider, String region) {
    AmazonS3ClientBuilder.standard()
        .withRegion(region)
        .withCredentials(awsCredentialsProvider)
        .build()
        .deleteBucket(name);
  }

  /**
   * Checks if the selected credentials are valid.
   *
   * @return result of credentials validation.
   */
  public boolean isCallerValid(AWSCredentialsProvider awsCredentialsProvider, String region) {
    try {
      return !Objects.isNull(
          AWSSecurityTokenServiceClientBuilder.standard()
              .withRegion(region)
              .withCredentials(awsCredentialsProvider)
              .build()
              .getCallerIdentity(new GetCallerIdentityRequest())
              .getArn());
    } catch (AWSSecurityTokenServiceException e) {
      return false;
    }
  }
}
