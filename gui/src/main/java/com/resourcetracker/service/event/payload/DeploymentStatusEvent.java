package com.resourcetracker.service.event.payload;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Represents deployment status event used for state management. */
@Getter
public class DeploymentStatusEvent extends ApplicationEvent {
  private final boolean deploymentAvailable;

  public DeploymentStatusEvent(boolean deploymentAvailable) {
    super(deploymentAvailable);

    this.deploymentAvailable = deploymentAvailable;
  }
}
