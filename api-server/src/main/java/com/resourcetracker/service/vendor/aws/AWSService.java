package com.resourcetracker.service.vendor.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.profile.path.cred.CredentialsDefaultLocationProvider;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesRequest;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesResult;
import com.amazonaws.services.ec2.model.NetworkInterface;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import com.amazonaws.services.ecs.model.*;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.resourcetracker.entity.AWSDeploymentResult;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.AWSRunTaskFailureException;
import com.resourcetracker.service.config.ConfigService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Provides access to AWS SDK to perform subprocesses
 * needed for AWS provider management.
 */
public class AWSService {
    private static final Logger logger = LogManager.getLogger(AWSService.class);

    private final AWSCredentialsProvider awsCredentialsProvider;

    public AWSService(AWSCredentialsProvider awsCredentialsProvider) {
        this.awsCredentialsProvider = awsCredentialsProvider;
    }

    /**
     * Composes AWS Credentials Provider used by AWS SDK clients.
     * @param accessKey access key exposed by AWS client.
     * @param secretKey secret key exposed by AWS client.
     * @return composed AWS Credentials Provider.
     */
    static public AWSCredentialsProvider getAWSCredentialsProvider(String accessKey, String secretKey) {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }

    /**
     * Composes ECS Task details into deployment readable format.
     * @param src raw details returned by Terraform deployment application.
     * @return composed ECS Task details.
     */
    public AWSDeploymentResult getEcsTaskRunDetails(String src) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.reader().forType(new TypeReference<PropertiesEntity>() {
        });
        try {
            return reader.<AWSDeploymentResult>readValues(src).readAll().getFirst();
        } catch (IOException e) {
            logger.fatal(e.getMessage());
        }

        return null;
    }

    /**
     * Runs ECS Task with the given configuration properties.
     * @param awsDeploymentResult composed ECS Task details.
     * @throws AWSRunTaskFailureException thrown when AWS Task run action fails.
     */
    public void runEcsTask(AWSDeploymentResult awsDeploymentResult) throws AWSRunTaskFailureException {
        AwsVpcConfiguration awsVpcConfiguration = new AwsVpcConfiguration()
                .withSubnets(awsDeploymentResult.getResourceTrackerMainSubnetId().getValue())
                .withSecurityGroups(awsDeploymentResult.getResourceTrackerSecurityGroup().getValue());

        NetworkConfiguration networkConfiguration = new NetworkConfiguration()
                .withAwsvpcConfiguration(awsVpcConfiguration);

        RunTaskRequest runTaskRequest = new RunTaskRequest()
                .withTaskDefinition(awsDeploymentResult.getEcsTaskDefinition().getValue())
                .withCluster(awsDeploymentResult.getEcsCluster().getValue())
                .withNetworkConfiguration(networkConfiguration)
                .withLaunchType(LaunchType.FARGATE);

        RunTaskResult runTaskResult = AmazonECSClientBuilder
                .standard()
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
     * @param runTaskResult embedded result of ECS Task run action.
     * @return gathered output of ECS Task run action.
     */
    private String gatherRunEcsTaskResultOutput(RunTaskResult runTaskResult) {
        return runTaskResult.getFailures()
                .stream()
                .map(element ->
                        String.format(
                                "%s, %s, %s",
                                element.getArn(),
                                element.getDetail(),
                                element.getReason()))
                .collect(Collectors.joining(";"));
    }

    /**
     * Retrieves remote machine address from the given ECS Task details.
     * @param awsDeploymentResult composed ECS Task details.
     * @return remote machine address.
     */
    public String getMachineAddress(AWSDeploymentResult awsDeploymentResult) {
        String ecsTaskArn = getEcsTaskArn("");

        String ecsTaskNetworkInterfaceId = getEcsTaskNetworkInterfaceId("", ecsTaskArn);

        return getEcsTaskPublicId(ecsTaskNetworkInterfaceId);
    }

    /**
     *
     * @param ecsClusterId
     * @return
     */
    private String getEcsTaskArn(String ecsClusterId) {
        ListTasksRequest listTasksRequest = new ListTasksRequest()
                .withCluster(ecsClusterId);

        ListTasksResult listTasksResult = AmazonECSClientBuilder
            .standard()
            .withCredentials(awsCredentialsProvider)
            .build()
            .listTasks(listTasksRequest);

        return listTasksResult.getTaskArns().get(0);
    }

    /**
     *
     * @param ecsClusterId
     * @param ecsTaskArn
     * @return
     */
    private String getEcsTaskNetworkInterfaceId(String ecsClusterId, String ecsTaskArn) {
            DescribeTasksRequest describeTaskRequest = new DescribeTasksRequest()
                    .withCluster(ecsClusterId)
                    .withTasks(List.of(ecsTaskArn));

        DescribeTasksResult describeTasksResult = AmazonECSClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .build()
                .describeTasks(describeTaskRequest);

        Task task = describeTasksResult.getTasks().get(0);

        String taskAttachmentId = task
                .getContainers()
                .stream()
                .filter(element -> !Objects.equals(element.getName(), "kafka"))
                .map(element -> element.getNetworkInterfaces().get(0).getAttachmentId())
                .toList()
                .get(0);

        return task
                .getAttachments()
                .stream()
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
     *
     * @param networkInterfaceId
     * @return
     */
    private String getEcsTaskPublicId(String networkInterfaceId) {
        DescribeNetworkInterfacesRequest describeNetworkInterfacesRequest = new DescribeNetworkInterfacesRequest()
                .withNetworkInterfaceIds(List.of(networkInterfaceId));

        DescribeNetworkInterfacesResult describeNetworkInterfacesResult = AmazonEC2ClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .build()
                .describeNetworkInterfaces(describeNetworkInterfacesRequest);

        NetworkInterface networkInterfaces = describeNetworkInterfacesResult.getNetworkInterfaces().get(0);

        return networkInterfaces.getAssociation().getPublicIp();
    }

    /**
     * Checks if S3 bucket with the given name exists.
     * @param name name of the S3 bucket.
     * @return result of the check.
     */
    public boolean isS3BucketExist(String name) {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .build()
                .doesBucketExistV2(name);
    }

    /**
     * Creates S3 bucket with the given name.
     * @param name name of the S3 bucket.
     */
    public void createS3Bucket(String name) {
        AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .build()
                .createBucket(name);
    }

    /**
     * Removes S3 bucket with the given name
     * @param name name of the S3 bucket.
     */
    public void removeS3Bucket(String name) {
        AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .build()
                .deleteBucket(name);
    }
}
