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

import java.util.Objects;

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
    if (Objects.isNull(LocalState.getPrevWindowHeight())) {
      return false;
    }

//    System.out.println("before");
//    System.out.println(prevWindowHeight);
//    System.out.println(windowHeight);
//    System.out.println("after");

    return !prevWindowHeight.equals(windowHeight);
  }

  /**
   *
   * @return
   */
  public static Boolean isWindowWidthChanged() {
    if (Objects.isNull(LocalState.getPrevWindowWidth())) {
      return false;
    }

    return !prevWindowWidth.equals(windowWidth);
  }

  /**
   *
   */
  public static synchronized void synchronizeWindowHeight() {
    LocalState.setPrevWindowHeight(LocalState.getWindowHeight());
  }

  /**
   *
   */
  public static synchronized void synchronizeWindowWidth() {
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
    if (Objects.isNull(LocalState.getPrevWindowHeight())) {
      LocalState.setPrevWindowHeight(event.getHeight());
    }

    LocalState.setWindowHeight(event.getHeight());
  }

  /**
   * Handles changes of window width update.
   * @param event window width update event, which contains new window width.
   */
  @EventListener
  void handleWindowWidthUpdateEvent(WindowWidthUpdateEvent event) {
    if (Objects.isNull(LocalState.getPrevWindowWidth())) {
      LocalState.setPrevWindowWidth(event.getWidth());
    }

    LocalState.setWindowWidth(event.getWidth());
  }
}
