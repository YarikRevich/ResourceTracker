package com.resourcetracker.service.event.state;

import com.resourcetracker.service.event.state.payload.ConnectionStatusEvent;
import com.resourcetracker.service.event.state.payload.WindowHeightUpdateEvent;
import com.resourcetracker.service.event.state.payload.WindowWidthUpdateEvent;
import javafx.geometry.Rectangle2D;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Represents local state management model, which is used to handle application state changes and
 * exposes them for the further usage.
 */
@Component
public class LocalState {
  @Getter @Setter private static Boolean connectionEstablished;

  @Getter @Setter private static Double prevWindowHeight;

  @Getter @Setter private static Double windowHeight;

  @Getter @Setter private static Double prevWindowWidth;

  @Getter @Setter private static Double windowWidth;

  /**
   *
   * @return
   */
  public static Boolean isWindowHeightChanged() {
    return prevWindowHeight == null || (windowHeight != null && !prevWindowHeight.equals(windowHeight));
  }

  /**
   *
   * @return
   */
  public static Boolean isWindowWidthChanged() {
    return prevWindowWidth == null || (windowWidth != null && !prevWindowWidth.equals(windowWidth));
  }

  /**
   *
   */
  public static void synchronizeWindowHeight() {
    LocalState.setPrevWindowHeight(LocalState.getWindowHeight());
  }

  /**
   *
   */
  public static void synchronizeWindowWidth() {
    LocalState.setPrevWindowWidth(LocalState.getWindowWidth());
  }

  /**
   * Handles changes of connection establishment status.
   * @param event connection status event, which contains connection establishment status.
   */
  @EventListener
  void handleConnectionStatusEvent(ConnectionStatusEvent event) {
    LocalState.setConnectionEstablished(event.isConnectionEstablished());
  }

  /**
   * Handles changes of window height update.
   * @param event window height update event, which contains new window height.
   */
  @EventListener
  void handleWindowHeightUpdateEvent(WindowHeightUpdateEvent event) {
    LocalState.setWindowHeight(event.getHeight());
  }

  /**
   * Handles changes of window width update.
   * @param event window width update event, which contains new window width.
   */
  @EventListener
  void handleWindowHeightUpdateEvent(WindowWidthUpdateEvent event) {
    LocalState.setWindowWidth(event.getWidth());
  }
}
