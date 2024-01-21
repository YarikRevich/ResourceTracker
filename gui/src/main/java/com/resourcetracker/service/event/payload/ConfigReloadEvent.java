package com.resourcetracker.service.event.payload;

import com.resourcetracker.service.event.common.EventType;
import com.resourcetracker.service.event.common.IEvent;
import org.springframework.context.ApplicationEvent;

/** Represents config reload event used for state management. */
public class ConfigReloadEvent extends ApplicationEvent implements IEvent {
  public ConfigReloadEvent() {
    super(new Object());
  }

  /**
   * @see IEvent
   */
  public EventType getEventType() {
    return EventType.CONFIG_RELOAD_EVENT;
  }
}
