package com.resourcetracker.service.event.state.payload;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** */
@Getter
public class ConnectionStatusEvent extends ApplicationEvent {
  private final boolean connectionEstablished;

  public ConnectionStatusEvent(boolean connectionEstablished) {
    super(connectionEstablished);

    this.connectionEstablished = connectionEstablished;
  }
}
