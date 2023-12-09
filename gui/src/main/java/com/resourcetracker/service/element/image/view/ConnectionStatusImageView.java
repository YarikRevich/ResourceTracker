package com.resourcetracker.service.element.image.view;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElementActualizable;
import com.resourcetracker.service.element.image.collection.ConnectionStatusImageCollection;
import com.resourcetracker.service.event.state.LocalState;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.scene.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectionStatusImageView implements IElementActualizable<Group> {
  @Autowired private PropertiesEntity properties;

  private static Group connectionStatusImageView;

  public ConnectionStatusImageView() {
    Group connectionStatusImageView = new Group();
    connectionStatusImageView
        .getChildren()
        .add(ConnectionStatusImageCollection.getFailedConnectionStatusImage());

    ConnectionStatusImageView.connectionStatusImageView = connectionStatusImageView;
  }

  /**
   * @see IElementActualizable
   */
  @Override
  public Group getContent() {
    return connectionStatusImageView;
  }

  /**
   * @param value window width value.
   */
  @Override
  public void prefWidth(Double value) {}

  /**
   * @param value window height value.
   */
  @Override
  public void prefHeight(Double value) {}

  /**
   * @see IElementActualizable
   */
  @Override
  public void handleBackgroundUpdates() {
    ScheduledExecutorService scheduledExecutorService =
        Executors.newSingleThreadScheduledExecutor();
    ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    scheduledExecutorService.scheduleAtFixedRate(
        () ->
            executorService.execute(
                () -> {
                  connectionStatusImageView.getChildren().removeAll();

                  if (LocalState.isConnectionEstablished()) {
                    connectionStatusImageView
                        .getChildren()
                        .add(ConnectionStatusImageCollection.getSuccessfulConnectionStatusImage());
                  } else {
                    connectionStatusImageView
                        .getChildren()
                        .add(ConnectionStatusImageCollection.getFailedConnectionStatusImage());
                  }
                }),
        0,
        properties.getProcessBackgroundPeriod(),
        TimeUnit.MILLISECONDS);
  }
}
