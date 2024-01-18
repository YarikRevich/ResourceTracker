package com.resourcetracker.service.event.payload;

import com.resourcetracker.model.TopicLogsResult;
import com.resourcetracker.service.event.common.EventType;
import com.resourcetracker.service.event.common.IEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Represents status update event used for state management. */
@Getter
public class DeploymentStateUpdateEvent extends ApplicationEvent implements IEvent {
  private final TopicLogsResult deploymentState;

  public DeploymentStateUpdateEvent(TopicLogsResult deploymentState) {
    super(deploymentState);

    this.deploymentState = deploymentState;
  }

  /**
   * @see IEvent
   */
  public EventType getEventType() {
    return EventType.DEPLOYMENT_STATE_UPDATE_EVENT;
  }
}
