package com.resourcetracker.service.event.state;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.common.WindowHelper;
import com.resourcetracker.service.event.state.payload.ConnectionStatusEvent;
import com.resourcetracker.service.event.state.payload.WindowHeightUpdateEvent;
import com.resourcetracker.service.event.state.payload.WindowWidthUpdateEvent;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Represents local state management model, which is used to handle application state changes and
 * exposes them for the further usage.
 */
@Component
public class LocalState {
  @Autowired private PropertiesEntity properties;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Getter @Setter private static Boolean connectionEstablished = false;

  @Getter @Setter private static Double prevWindowHeight;

  @Getter @Setter private static Double windowHeight;

  @Getter @Setter private static Double prevWindowWidth;

  @Getter @Setter private static Double windowWidth;

  @Getter private static CountDownLatch locker = new CountDownLatch(1);

  /**
   * @return
   */
  public static synchronized Boolean isWindowHeightChanged() {
    if (Objects.isNull(LocalState.getPrevWindowHeight())
        && !Objects.isNull(LocalState.getWindowHeight())) {
      return true;
    } else if (Objects.isNull(LocalState.getPrevWindowHeight())) {
      return false;
    }

    return !prevWindowHeight.equals(windowHeight);
  }

  /**
   * @return
   */
  public static synchronized Boolean isWindowWidthChanged() {
    if (Objects.isNull(LocalState.getPrevWindowWidth())
        && !Objects.isNull(LocalState.getWindowWidth())) {
      return true;
    } else if (Objects.isNull(LocalState.getPrevWindowWidth())) {
      return false;
    }

    return !prevWindowWidth.equals(windowWidth);
  }

  /** */
  public static synchronized void synchronizeWindowHeight() {
    LocalState.setPrevWindowHeight(LocalState.getWindowHeight());
  }

  /** */
  public static synchronized void synchronizeWindowWidth() {
    LocalState.setPrevWindowWidth(LocalState.getWindowWidth());
  }

  @EventListener(classes = {ContextRefreshedEvent.class})
  public void eventListen(ContextRefreshedEvent contextRefreshedEvent) {
    Rectangle2D defaultBounds = Screen.getPrimary().getVisualBounds();

    Rectangle2D window =
        WindowHelper.getSizeWithScale(
            defaultBounds.getWidth(),
            defaultBounds.getHeight(),
            properties.getWindowMainScaleWidth(),
            properties.getWindowMainScaleHeight());

    applicationEventPublisher.publishEvent(new WindowWidthUpdateEvent(window.getWidth()));
    applicationEventPublisher.publishEvent(new WindowHeightUpdateEvent(window.getHeight()));
  }

  /**
   * Handles changes of connection establishment status.
   *
   * @param event connection status event, which contains connection establishment status.
   */
  @EventListener
  void handleConnectionStatusEvent(ConnectionStatusEvent event) {
    LocalState.setConnectionEstablished(event.isConnectionEstablished());
  }

  /**
   * Handles changes of window height update.
   *
   * @param event window height update event, which contains new window height.
   */
  @EventListener
  synchronized void handleWindowHeightUpdateEvent(WindowHeightUpdateEvent event) {
    LocalState.setWindowHeight(event.getHeight());
  }

  /**
   * Handles changes of window width update.
   *
   * @param event window width update event, which contains new window width.
   */
  @EventListener
  synchronized void handleWindowWidthUpdateEvent(WindowWidthUpdateEvent event) {
    LocalState.setWindowWidth(event.getWidth());
  }
}
