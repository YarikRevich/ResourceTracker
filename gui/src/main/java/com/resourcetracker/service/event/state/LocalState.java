package com.resourcetracker.service.event.state;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.common.ElementHelper;
import com.resourcetracker.service.event.payload.ConnectionStatusEvent;
import com.resourcetracker.service.event.payload.DeploymentStatusEvent;
import com.resourcetracker.service.event.payload.MainWindowHeightUpdateEvent;
import com.resourcetracker.service.event.payload.MainWindowWidthUpdateEvent;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * Represents local state management model, which is used to handle application state changes and
 * exposes them for the further usage.
 */
@EnableAsync
@Component
public class LocalState {
  @Autowired private PropertiesEntity properties;

  @Autowired private ApplicationEventPublisher applicationEventPublisher;

  @Getter @Setter private static Boolean deploymentAvailable = false;

  @Getter @Setter private static Boolean connectionEstablished = false;

  @Getter @Setter private static Double prevMainWindowHeight;

  @Getter @Setter private static Double mainWindowHeight;

  @Getter @Setter private static Double prevMainWindowWidth;

  @Getter @Setter private static Double mainWindowWidth;

  private static final CountDownLatch mainWindowWidthUpdateMutex = new CountDownLatch(1);

  private static final CountDownLatch mainWindowHeightUpdateMutex = new CountDownLatch(1);

  /**
   * @return
   */
  @SneakyThrows
  public static synchronized Boolean isWindowHeightChanged() {
    if (Objects.isNull(LocalState.getPrevMainWindowHeight())
        && !Objects.isNull(LocalState.getMainWindowHeight())) {
      return true;
    } else if (Objects.isNull(LocalState.getPrevMainWindowHeight())) {
      mainWindowHeightUpdateMutex.await();

      return false;
    }

    return !prevMainWindowHeight.equals(mainWindowHeight);
  }

  /**
   * @return
   */
  @SneakyThrows
  public static synchronized Boolean isWindowWidthChanged() {
    if (Objects.isNull(LocalState.getPrevMainWindowWidth())
        && !Objects.isNull(LocalState.getMainWindowWidth())) {
      return true;
    } else if (Objects.isNull(LocalState.getPrevMainWindowWidth())) {
      mainWindowWidthUpdateMutex.await();

      return false;
    }

    return !prevMainWindowWidth.equals(mainWindowWidth);
  }

  /** */
  public static synchronized void synchronizeWindowHeight() {
    LocalState.setPrevMainWindowHeight(LocalState.getMainWindowHeight());
  }

  /** */
  public static synchronized void synchronizeWindowWidth() {
    LocalState.setPrevMainWindowWidth(LocalState.getMainWindowWidth());
  }

  @EventListener(classes = {ContextRefreshedEvent.class})
  public void eventListen(ContextRefreshedEvent contextRefreshedEvent) {
    Rectangle2D defaultBounds = Screen.getPrimary().getVisualBounds();

    Rectangle2D window =
        ElementHelper.getSizeWithScale(
            defaultBounds.getWidth(),
            defaultBounds.getHeight(),
            properties.getWindowMainScaleMinWidth(),
            properties.getWindowMainScaleMinHeight());

    applicationEventPublisher.publishEvent(new MainWindowWidthUpdateEvent(window.getWidth()));
    applicationEventPublisher.publishEvent(new MainWindowHeightUpdateEvent(window.getHeight()));
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
   * Handles changes of deployment availability status.
   *
   * @param event deployment status event, which contains deployment availability status.
   */
  @EventListener
  void handleDeploymentStatusEvent(DeploymentStatusEvent event) {
    LocalState.setDeploymentAvailable(event.isDeploymentAvailable());
  }

  /**
   * Handles changes of main window height update.
   *
   * @param event main window height update event, which contains new window height.
   */
  @EventListener
  synchronized void handleMainWindowHeightUpdateEvent(MainWindowHeightUpdateEvent event) {
    LocalState.setMainWindowHeight(event.getHeight());

    mainWindowHeightUpdateMutex.countDown();
  }

  /**
   * Handles changes of main window width update.
   *
   * @param event main window width update event, which contains new window width.
   */
  @EventListener
  synchronized void handleMainWindowWidthUpdateEvent(MainWindowWidthUpdateEvent event) {
    LocalState.setMainWindowWidth(event.getWidth());

    mainWindowWidthUpdateMutex.countDown();
  }
}
