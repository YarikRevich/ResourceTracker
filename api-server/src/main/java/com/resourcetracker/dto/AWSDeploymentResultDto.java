package com.resourcetracker.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;

/** Represents dto used to serialize AWS deployment result. */
public class AWSDeploymentResultDto {
  @Getter
  public static class RawResult {
    private String value;

    private Boolean sensitive;

    private String type;
  }

  @JsonAlias({"ecs_task_definition"})
  public RawResult ecsTaskDefinition;

  @JsonAlias({"ecs_service"})
  public RawResult ecsService;

  @JsonAlias({"ecs_cluster"})
  public RawResult ecsCluster;

  @JsonAlias({"ecs_task_execution_role"})
  public RawResult ecsTaskExecutionRole;

  @JsonAlias({"resourcetracker_security_group"})
  public RawResult resourceTrackerSecurityGroup;

  @JsonAlias({"resourcetracker_main_subnet_id"})
  public RawResult resourceTrackerMainSubnetId;

  @JsonSetter
  public void setEcsTaskDefinition(RawResult ecsTaskDefinition) {
    this.ecsTaskDefinition = ecsTaskDefinition;
  }

  @JsonSetter
  public void setEcsService(RawResult ecsService) {
    this.ecsService = ecsService;
  }

  @JsonSetter
  public void setEcsCluster(RawResult ecsCluster) {
    this.ecsCluster = ecsCluster;
  }

  @JsonSetter
  public void setEcsTaskExecutionRole(RawResult ecsTaskExecutionRole) {
    this.ecsTaskExecutionRole = ecsTaskExecutionRole;
  }

  @JsonSetter
  public void setResourceTrackerSecurityGroup(RawResult resourceTrackerSecurityGroup) {
    this.resourceTrackerSecurityGroup = resourceTrackerSecurityGroup;
  }

  @JsonSetter
  public void setResourceTrackerMainSubnetId(RawResult resourceTrackerMainSubnetId) {
    this.resourceTrackerMainSubnetId = resourceTrackerMainSubnetId;
  }

  @JsonGetter
  public RawResult getEcsTaskDefinition() {
    return ecsTaskDefinition;
  }

  @JsonGetter
  public RawResult getEcsService() {
    return ecsService;
  }

  @JsonGetter
  public RawResult getEcsCluster() {
    return ecsCluster;
  }

  @JsonGetter
  public RawResult getEcsTaskExecutionRole() {
    return ecsTaskExecutionRole;
  }

  @JsonGetter
  public RawResult getResourceTrackerSecurityGroup() {
    return resourceTrackerSecurityGroup;
  }

  @JsonGetter
  public RawResult getResourceTrackerMainSubnetId() {
    return resourceTrackerMainSubnetId;
  }
}
