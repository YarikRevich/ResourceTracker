package com.resourcetracker.services.provider.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
	public RawResult ecsTaskDefinition;

	@JsonAlias({"ecs_cluster"})
	public RawResult ecsCluster;

//	@JsonAlias({"main_subnet"})
//	public RawResult mainSubnet;
//
//	@JsonAlias({"security_group"})
//	public RawResult securityGroup;

	@JsonSetter
	public void setEcsTaskDefinition(RawResult ecsTaskDefinition) {
		this.ecsTaskDefinition = ecsTaskDefinition;
	}

	@JsonSetter
	public void setEcsCluster(RawResult ecsCluster) {
		this.ecsCluster = ecsCluster;
	}

//	@JsonSetter
//	public void setMainSubnet(RawResult mainSubnet) {
//		this.mainSubnet = mainSubnet;
//	}
//
//	@JsonSetter
//	public void setSecurityGroup(RawResult securityGroup) {
//		this.securityGroup = securityGroup;
//	}

	@JsonGetter
	public RawResult getEcsTaskDefinition() {
		return ecsTaskDefinition;
	}

	@JsonGetter
	public RawResult getEcsCluster() {
		return ecsCluster;
	}

//	@JsonGetter
//	public RawResult getMainSubnet() {
//		return mainSubnet;
//	}
//
//	@JsonGetter
//	public RawResult getSecurityGroup() {
//		return securityGroup;
//	}

	public static AWSResult fromJson(String src){
		ObjectMapper mapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			return mapper.readValue(src, new TypeReference<>() {
			});
		} catch (IOException e) {
				 e.printStackTrace();
		}
		return null;
	}
}
