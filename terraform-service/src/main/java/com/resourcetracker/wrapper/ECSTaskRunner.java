package com.resourcetracker.wrapper;

import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import com.amazonaws.services.ecs.model.*;
import com.resourcetracker.entity.AWSResult;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ECSTaskRunner {
	private AWSResult output;

	public ECSTaskRunner(AWSResult output){
		this.output = output;
	}

	public URL run() {
		final AmazonECS ecs = AmazonECSClientBuilder.standard().build();
		RunTaskRequest runTaskRequest = new RunTaskRequest();
//		runTaskRequest.setTaskDefinition(output.getEcsTaskDefinition().getValue());
//		runTaskRequest.setCluster(output.getEcsCluster().getValue());
		runTaskRequest.setNetworkConfiguration(this.getNetworkConfiguration());
		RunTaskResult runTaskResult = ecs.runTask(runTaskRequest);
		try {
			runTaskResult.wait();
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		System.out.println(runTaskResult.toString());
//		runTaskResult.getTasks().forEach((Task task) -> {
//
//		});
		try {
			return new URL("");
		} catch (MalformedURLException e){
			e.printStackTrace();
		}
		return null;
	}

	private NetworkConfiguration getNetworkConfiguration(){
		NetworkConfiguration networkConfiguration = new NetworkConfiguration();
		networkConfiguration.setAwsvpcConfiguration(this.getAwsvpcConfiguration());
		return networkConfiguration;
	}

	private AwsVpcConfiguration getAwsvpcConfiguration(){
		AwsVpcConfiguration awsVpcConfiguration = new AwsVpcConfiguration();
		awsVpcConfiguration.setSecurityGroups(this.getSecurityGroups());
		awsVpcConfiguration.setSubnets(this.getSubnets());
		return awsVpcConfiguration;
	}

	private ArrayList<String> getSecurityGroups(){
		ArrayList<String> securityGroups = new ArrayList<String>();
//		securityGroups.add(output.getSecurityGroup().getValue());
		return securityGroups;
	}

	private ArrayList<String> getSubnets(){
		ArrayList<String> subnets = new ArrayList<String>();
//		subnets.add(output.getMainSubnet().getValue());
		return subnets;
	}
}
