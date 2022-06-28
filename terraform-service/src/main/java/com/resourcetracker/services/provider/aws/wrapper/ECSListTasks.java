package com.resourcetracker.services.provider.aws.wrapper;

import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import com.amazonaws.services.ecs.model.ListTasksRequest;
import com.amazonaws.services.ecs.model.ListTasksResult;
import com.resourcetracker.services.provider.aws.entity.AWSResult;
import com.resourcetracker.services.provider.aws.wrapper.entity.ECSListTasksResult;

public class ECSListTasks {
	private AWSResult awsResult;

	public ECSListTasks(AWSResult awsResult) {
		this.awsResult = awsResult;
	}

	public ECSListTasksResult run(){
		ECSListTasksResult ecsListTasksResult = new ECSListTasksResult();

		final AmazonECS ecs = AmazonECSClientBuilder.standard().build();

		ListTasksRequest listTasksRequest = new ListTasksRequest();
		listTasksRequest.setCluster(awsResult.getEcsCluster().getValue());
		ListTasksResult listTasksResult = ecs.listTasks(listTasksRequest);

		ecsListTasksResult.setTaskArn(listTasksResult.getTaskArns().get(0));
		return ecsListTasksResult;
	}
}
