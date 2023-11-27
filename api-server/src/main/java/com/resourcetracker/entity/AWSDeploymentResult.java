package com.resourcetracker.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;

public class AWSDeploymentResult {
    @Getter
    public static class RawResult{
        String value;
    }

    @JsonAlias({"ecs_task_definition"})
    public RawResult ecsTaskDefinition;

    @JsonAlias({"ecs_cluster"})
    public RawResult ecsCluster;

    @JsonAlias({"resourcetracker_security_group"})
    public RawResult resourceTrackerSecurityGroup;

    @JsonAlias({"resourcetracker_main_subnet_id"})
    public RawResult resourceTrackerMainSubnetId;

    @JsonSetter
    public void setEcsTaskDefinition(RawResult ecsTaskDefinition) {
        this.ecsTaskDefinition = ecsTaskDefinition;
    }

    @JsonSetter
    public void setEcsCluster(RawResult ecsCluster) {
        this.ecsCluster = ecsCluster;
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
    public RawResult getEcsCluster() {
        return ecsCluster;
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
