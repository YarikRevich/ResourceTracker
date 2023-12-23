package com.resourcetracker.service.event.payload;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** */
@Getter
public class MainWindowHeightUpdateEvent extends ApplicationEvent {
  private final Double height;

  public MainWindowHeightUpdateEvent(Double height) {
    super(height);

    this.height = height;
  }
}
