package com.resourcetracker.service.event.state.payload;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** */
@Getter
public class WindowHeightUpdateEvent extends ApplicationEvent {
  private final Double height;

  public WindowHeightUpdateEvent(Double height) {
    super(height);

    this.height = height;
  }
}
