package com.resourcetracker.services.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.ecs.model.NetworkInterface;
import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import com.amazonaws.services.ecs.model.DescribeTasksRequest;
import com.amazonaws.services.ecs.model.DescribeTasksResult;
import com.amazonaws.services.ecs.model.Task;
import com.amazonaws.services.ecs.model.Attachment;
import com.amazonaws.services.ecs.model.Container;
import com.amazonaws.services.ecs.model.KeyValuePair;
import com.resourcetracker.Constants;
import com.resourcetracker.services.provider.entity.AWSResult;
import com.resourcetracker.services.wrapper.entity.ECSDescribeTaskResult;
import com.resourcetracker.services.wrapper.entity.ECSListTasksResult;

public class ECSDescribeTask {
	private AWSResult awsResult;
	private ECSListTasksResult ecsListTasksResult;

	public ECSDescribeTask(AWSResult awsResult, ECSListTasksResult ecsListTasksResult) {
		this.awsResult = awsResult;
		this.ecsListTasksResult = ecsListTasksResult;
	}

	public ECSDescribeTaskResult run(){
		ECSDescribeTaskResult ecsDescribeTaskResult = new ECSDescribeTaskResult();

		final AmazonECS ecs = AmazonECSClientBuilder.standard().build();

		DescribeTasksRequest describeTaskRequest = new DescribeTasksRequest();
		describeTaskRequest.setCluster(this.awsResult.getEcsCluster().getValue());
		List<String> tasks = new ArrayList<>();
		tasks.add(this.ecsListTasksResult.getTaskArn());
		describeTaskRequest.setTasks(tasks);

		DescribeTasksResult describeTasksResult = ecs.describeTasks(describeTaskRequest);

		Task task = describeTasksResult.getTasks().get(0);
		String kafkaContainerAttachementId = "";
		List<Container> containers = task.getContainers();
			for (Container container : containers){
				if (container.getName() == Constants.KAFKA_CONTAINER_NAME){
					NetworkInterface networkInterface = container.getNetworkInterfaces().get(0);
					kafkaContainerAttachementId = networkInterface.getAttachmentId();
					break;
				}
			}
		List<Attachment> attachments = task.getAttachments();
			for (Attachment attachment : attachments){
				if (attachment.getId() == kafkaContainerAttachementId){
					List<KeyValuePair> details = attachment.getDetails();
					for (KeyValuePair detail : details) {
						if (detail.getName() == "networkInterfaceId"){
							ecsDescribeTaskResult.setNetworkInterfaceID(detail.getValue());
						}
					}
					break;
				}
			}

		return ecsDescribeTaskResult;
	}
}
