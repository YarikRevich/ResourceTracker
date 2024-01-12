package com.resourcetracker.service.event.payload;

import com.resourcetracker.service.event.common.EventType;
import com.resourcetracker.service.event.common.IEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Represents deployment status event used for state management. */
@Getter
public class DeploymentStatusEvent extends ApplicationEvent implements IEvent {
  private final boolean deploymentAvailable;

  public DeploymentStatusEvent(boolean deploymentAvailable) {
    super(deploymentAvailable);

    this.deploymentAvailable = deploymentAvailable;
  }

  /**
   * @see IEvent
   */
  public EventType getEventType() {
    return EventType.DEPLOYMENT_STATUS_EVENT;
  }
}
