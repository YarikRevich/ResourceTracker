package com.resourcetracker.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

public class AWSResult implements Serializable {
	public static class RawResult{
		public String value;

		public String getValue() {
			return value;
		}
	}

	@JsonAlias({"ecs_task_definition"})
	public Object ecsTaskDefinition;

	@JsonAlias({"ecs_cluster"})
	public Object ecsCluster;

	@JsonAlias({"main_subnet"})
	public Object mainSubnet;

	@JsonAlias({"security_group"})
	public Object securityGroup;

	@JsonSetter
	public void setEcsTaskDefinition(Object ecsTaskDefinition) {
		this.ecsTaskDefinition = ecsTaskDefinition;
	}

	@JsonSetter
	public void setEcsCluster(Object ecsCluster) {
		this.ecsCluster = ecsCluster;
	}

	@JsonSetter
	public void setMainSubnet(Object mainSubnet) {
		this.mainSubnet = mainSubnet;
	}

	@JsonSetter
	public void setSecurityGroup(Object securityGroup) {
		this.securityGroup = securityGroup;
	}

	@JsonGetter
	public Object getEcsTaskDefinition() {
		return ecsTaskDefinition;
	}

	@JsonGetter
	public Object getEcsCluster() {
		return ecsCluster;
	}

	@JsonGetter
	public Object getMainSubnet() {
		return mainSubnet;
	}

	@JsonGetter
	public Object getSecurityGroup() {
		return securityGroup;
	}

	public static AWSResult fromJson(String src){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(src, new TypeReference<>() {
			});
		} catch (IOException e) {
				 e.printStackTrace();
		}
		return null;
	}
}
