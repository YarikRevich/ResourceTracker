package com.resourcetracker.service.vendor.aws;

import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesRequest;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesResult;
import com.amazonaws.services.ec2.model.NetworkInterface;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import com.amazonaws.services.ecs.model.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.resourcetracker.entity.AWSDeploymentResult;
import com.resourcetracker.entity.PropertiesEntity;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * AWS implementation of Provider
 *
 * @author YarikRevich
 *
 */
@ApplicationScoped
public class AWSService {
    private static final Logger logger = LogManager.getLogger(AWSService.class);

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

    public String getMachineAddress(AWSDeploymentResult awsDeploymentResult) {
        String ecsTaskArn = getEcsTaskArn("");

        String ecsTaskNetworkInterfaceId = getEcsTaskNetworkInterfaceId("", ecsTaskArn);

        return getEcsTaskPublicId(ecsTaskNetworkInterfaceId);
    }

    private String getEcsTaskArn(String ecsClusterId) {
        ListTasksRequest listTasksRequest = new ListTasksRequest()
                .withCluster(ecsClusterId);

        ListTasksResult listTasksResult = AmazonECSClientBuilder
            .standard()
            .build()
            .listTasks(listTasksRequest);

        return listTasksResult.getTaskArns().get(0);
    }

    private String getEcsTaskNetworkInterfaceId(String ecsClusterId, String ecsTaskArn) {
            DescribeTasksRequest describeTaskRequest = new DescribeTasksRequest()
                    .withCluster(ecsClusterId)
                    .withTasks(List.of(ecsTaskArn));

        DescribeTasksResult describeTasksResult = AmazonECSClientBuilder
                .standard()
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

    private String getEcsTaskPublicId(String networkInterfaceId) {
        DescribeNetworkInterfacesRequest describeNetworkInterfacesRequest = new DescribeNetworkInterfacesRequest()
                .withNetworkInterfaceIds(List.of(networkInterfaceId));

        DescribeNetworkInterfacesResult describeNetworkInterfacesResult = AmazonEC2ClientBuilder
                .standard()
                .build()
                .describeNetworkInterfaces(describeNetworkInterfacesRequest);

        NetworkInterface networkInterfaces = describeNetworkInterfacesResult.getNetworkInterfaces().get(0);

        return networkInterfaces.getAssociation().getPublicIp();
    }
//  private void selectEnvVars(){
//    terraformAPIService.setEnvVar(Constants.AWS_SHARED_CREDENTIALS_FILE, configEntity.getCloud().getCredentials());
//    terraformAPIService.setEnvVar(Constants.AWS_PROFILE, configEntity.getCloud().getProfile());
//    terraformAPIService.setEnvVar(Constants.AWS_REGION, configEntity.getCloud().getRegion());
//  }
//
//  private void selectVars(){
//    terraformAPIService.setVar(Constants.TERRAFORM_CONTEXT_VAR, configEntity.toJSONAsContext());
//  }
//
//  private void selectBackendConfig(){
//    terraformAPIService.setBackendConfig(Constants.TERRAFORM_BACKEND_CONFIG_SHARED_CREDENTIALS_FILE, configEntity.getCloud().getCredentials());
//    terraformAPIService.setBackendConfig(Constants.TERRAFORM_BACKEND_PROFILE, configEntity.getCloud().getProfile());
//  }
}
