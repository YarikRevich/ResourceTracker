package com.resourcetracker.service.event.state;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Represents local state management model, which is used to handle application state changes and
 * exposes them for the further usage.
 */
@Component
public class LocalState {
  @Getter @Setter private static boolean connectionEstablished;

  /**
   * Handles changes of connection establishment status.
   *
   * @param event connection status event, which contains connection establishment status.
   */
  @EventListener
  void handleConnectionStatusEvent(ConnectionStatusEvent event) {
    LocalState.setConnectionEstablished(event.isConnectionEstablished());

    System.out.println("called");
  }
}
