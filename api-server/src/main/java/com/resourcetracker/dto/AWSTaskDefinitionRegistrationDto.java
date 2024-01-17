package com.resourcetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Represents ECS Task definition registration dto. */
@Getter
@AllArgsConstructor(staticName = "of")
public class AWSTaskDefinitionRegistrationDto {
  private AWSAgentTaskDefinitionRegistrationDto awsAgentTaskDefinitionRegistrationDto;

  private AWSKafkaTaskDefinitionRegistrationDto awsKafkaTaskDefinitionRegistrationDto;

  /** Represents common family group used by ECS Service to gather task definitions. */
  private String commonFamilyName;

  /** Represents common task execution role for ECS Service to operate running tasks. */
  private String commonTaskExecutionRole;

  /** Represents common AWS ResourceTracker CPU units reserved for Docker containers. */
  private String awsResourceTrackerCommonCPUUnits;

  /** Represents common AWS ResourceTracker Memory units reserved for Docker containers. */
  private String awsResourceTrackerCommonMemoryUnits;
}
