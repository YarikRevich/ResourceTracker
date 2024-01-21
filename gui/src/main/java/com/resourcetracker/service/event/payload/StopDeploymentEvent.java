package com.resourcetracker.service.event.payload;

import com.resourcetracker.service.event.common.EventType;
import com.resourcetracker.service.event.common.IEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Represents stop deployment event used for state management. */
@Getter
public class StopDeploymentEvent extends ApplicationEvent implements IEvent {
  public StopDeploymentEvent() {
    super(new Object());
  }

  /**
   * @see IEvent
   */
  public EventType getEventType() {
    return EventType.STOP_DEPLOYMENT_EVENT;
  }
}
