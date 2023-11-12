package com.resourcetracker.service.vendor.aws.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.IOException;

public class AWSResult {
  @Getter
  public static class RawResult{
    String value;
  }

  @JsonAlias({"ecs_task_definition"})
  public RawResult ecsTaskDefinition;

  @JsonAlias({"ecs_cluster"})
  public RawResult ecsCluster;

  @JsonSetter
  public void setEcsTaskDefinition(RawResult ecsTaskDefinition) {
    this.ecsTaskDefinition = ecsTaskDefinition;
  }

  @JsonSetter
  public void setEcsCluster(RawResult ecsCluster) {
    this.ecsCluster = ecsCluster;
  }

  @JsonGetter
  public RawResult getEcsTaskDefinition() {
    return ecsTaskDefinition;
  }

  @JsonGetter
  public RawResult getEcsCluster() {
    return ecsCluster;
  }

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
