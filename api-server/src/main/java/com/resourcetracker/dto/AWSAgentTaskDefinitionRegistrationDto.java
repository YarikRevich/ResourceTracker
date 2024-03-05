package com.resourcetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Represents ECS ResourceTracker Agent Task definition registration dto. */
@Getter
@AllArgsConstructor(staticName = "of")
public class AWSAgentTaskDefinitionRegistrationDto {
  /** Represents ResourceTracker Agent Docker image name. */
  private final String agentContainerImage;

  /** Represents ResourceTracker Agent Docker container name. */
  private final String agentContainerName;

  /** Represents serialized ResourceTracker Agent context entity. */
  private final String agentContext;

  /** Represents alias used for serialized ResourceTracker Agent context entity. */
  private final String agentContextAlias;

  /** Represents serialized ResourceTracker Agent version of Docker image to be used. */
  private final String agentVersion;
}
