package com.resourcetracker.service.event.payload;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** */
@Getter
public class MainWindowWidthUpdateEvent extends ApplicationEvent {
  private final Double width;

  public MainWindowWidthUpdateEvent(Double width) {
    super(width);

    this.width = width;
  }
}
