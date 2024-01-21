package com.resourcetracker.service.event.payload;

import com.resourcetracker.service.event.common.EventType;
import com.resourcetracker.service.event.common.IEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Represents start deployment event used for state management. */
@Getter
public class StartDeploymentEvent extends ApplicationEvent implements IEvent {
  public StartDeploymentEvent() {
    super(new Object());
  }

  /**
   * @see IEvent
   */
  public EventType getEventType() {
    return EventType.START_DEPLOYMENT_EVENT;
  }
}
