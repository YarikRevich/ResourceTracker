package com.resourcetracker.wrapper;

import com.amazonaws.services.ecs.AmazonECS;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import com.amazonaws.services.ecs.model.*;
import com.resourcetracker.entity.AWSOutput;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ECSTaskRunner {
	private AWSOutput output;

	public ECSTaskRunner(AWSOutput output){
		this.output = output;
	}

	public URL run() {
		final AmazonECS ecs = AmazonECSClientBuilder.standard().build();
		RunTaskRequest runTaskRequest = new RunTaskRequest();
		runTaskRequest.setTaskDefinition(output.getTaskDefinitionARN());
		runTaskRequest.setCluster(output.getClusterARN());
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
		securityGroups.add(output.getSecurityGroup());
		return securityGroups;
	}

	private ArrayList<String> getSubnets(){
		ArrayList<String> subnets = new ArrayList<String>();
		subnets.add(output.getSubnet());
		return subnets;
	}
}
