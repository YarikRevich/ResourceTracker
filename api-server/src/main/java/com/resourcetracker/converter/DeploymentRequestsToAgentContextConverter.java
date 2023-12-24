package com.resourcetracker.converter;

import com.resourcetracker.entity.AgentContextEntity;
import com.resourcetracker.entity.AgentContextEntity.Request;
import com.resourcetracker.model.DeploymentRequest;
import java.util.List;

/** Represents input deployment requests to agent context converter. */
public class DeploymentRequestsToAgentContextConverter {
  /**
   * Converts given deployment request to ResourceTracker Agent context entity.
   *
   * @param content given content to be converted.
   * @return converted ResourceTracker Agent context entity.
   */
  public static AgentContextEntity convert(List<DeploymentRequest> content) {
    return AgentContextEntity.of(
        content.stream()
            .map(
                element ->
                    Request.of(element.getName(), element.getScript(), element.getFrequency()))
            .toList());
  }
}
