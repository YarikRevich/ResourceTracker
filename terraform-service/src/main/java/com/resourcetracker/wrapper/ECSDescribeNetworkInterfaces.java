package com.resourcetracker.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.ec2.model.NetworkInterface;
import com.amazonaws.services.ec2.model.NetworkInterfaceAssociation;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesRequest;
import com.amazonaws.services.ec2.model.DescribeNetworkInterfacesResult;
import com.resourcetracker.wrapper.entity.ECSDescribeNetworkInterfacesResult;
import com.resourcetracker.wrapper.entity.ECSDescribeTaskResult;

public class ECSDescribeNetworkInterfaces {
	private ECSDescribeTaskResult ecsDescribeTaskResult;

	public ECSDescribeNetworkInterfaces(ECSDescribeTaskResult ecsDescribeTaskResult) {
		this.ecsDescribeTaskResult = ecsDescribeTaskResult;
	}

	public ECSDescribeNetworkInterfacesResult run(){
		ECSDescribeNetworkInterfacesResult ecsDescribeNetworkInterfacesResult = new ECSDescribeNetworkInterfacesResult();

		final AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard().build();

		DescribeNetworkInterfacesRequest describeNetworkInterfacesRequest = new DescribeNetworkInterfacesRequest();
		List<String> networkInterfaceIds = new ArrayList<>();
		networkInterfaceIds.add(this.ecsDescribeTaskResult.getNetworkInterfaceID());
		describeNetworkInterfacesRequest.setNetworkInterfaceIds(networkInterfaceIds);

		DescribeNetworkInterfacesResult describeNetworkInterfacesResult = ec2.describeNetworkInterfaces(describeNetworkInterfacesRequest);
		NetworkInterface networkInterfaces = describeNetworkInterfacesResult.getNetworkInterfaces().get(0);
		NetworkInterfaceAssociation networkInterfaceAssociation = networkInterfaces.getAssociation();

		ecsDescribeNetworkInterfacesResult.setContainerPublicIP(networkInterfaceAssociation.getPublicIp());
		return ecsDescribeNetworkInterfacesResult;
	}
}
