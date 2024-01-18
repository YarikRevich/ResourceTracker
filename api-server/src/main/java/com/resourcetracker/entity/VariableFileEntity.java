package com.resourcetracker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents variable file, which is created as a side effect of performed Terraform operations.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class VariableFileEntity {
  @JsonProperty("agent_context")
  private String agentContext;

  @JsonProperty("agent_version")
  private String agentVersion;
}
