package com.resourcetracker.services.provider.aws.wrapper.entity;

public class ECSDescribeNetworkInterfacesResult {
	private String containerPublicIP;

	public String getContainerPublicIP() {
		return this.containerPublicIP;
	}

	public void setContainerPublicIP(String containerPublicIP) {
		this.containerPublicIP = containerPublicIP;
	}
}
