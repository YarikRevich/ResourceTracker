package com.resourcetracker.entity;

public class AWSOutput{
	public String taskDefinitionARN;
	public String clusterARN;
	public String subnet;
	public String securityGroup;

	public String getTaskDefinitionARN() {
		return taskDefinitionARN;
	}

	public String getClusterARN() {
		return clusterARN;
	}

	public String getSubnet() {
		return subnet;
	}

	public String getSecurityGroup() {
		return securityGroup;
	}
}
