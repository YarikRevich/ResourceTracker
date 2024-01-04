package com.resourcetracker.service.event.payload;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Represents connection status event used for state management. */
@Getter
public class ConnectionStatusEvent extends ApplicationEvent {
  private final boolean connectionEstablished;

  public ConnectionStatusEvent(boolean connectionEstablished) {
    super(connectionEstablished);

    this.connectionEstablished = connectionEstablished;
  }
}
