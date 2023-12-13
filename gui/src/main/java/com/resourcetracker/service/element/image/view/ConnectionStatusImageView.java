package com.resourcetracker.service.element.image.view;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementActualizable;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.common.WindowHelper;
import com.resourcetracker.service.element.image.collection.ConnectionStatusImageCollection;

import java.util.UUID;

import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.state.LocalState;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents connection status image. */
@Service
public class ConnectionStatusImageView implements IElementActualizable, IElementResizable, IElement<SplitPane> {
  UUID id = UUID.randomUUID();

  private static final String CONNECTION_STATUS_ESTABLISH_FAILED = "connection is not established";

  private static final String CONNECTION_STATUS_ESTABLISH_SUCCEEDED = "connection is established";

  PropertiesEntity properties;

  public ConnectionStatusImageView(@Autowired PropertiesEntity properties) {
    Button connectionStatusImageView = new Button();
    connectionStatusImageView.setDisable(true);
    SplitPane splitPane = new SplitPane(connectionStatusImageView);
    splitPane.setTooltip(new Tooltip(CONNECTION_STATUS_ESTABLISH_FAILED));

    connectionStatusImageView.setGraphic(ConnectionStatusImageCollection.getFailedConnectionStatusImage(
            WindowHelper.getCircularElementSize(properties.getStatusImageScale())));

    ElementStorage.setElement(id, splitPane);
    ElementStorage.setActualizable(this);
    ElementStorage.setResizable(this);

    this.properties = properties;
  }

  /**
   * @see IElementActualizable
   */
  @Override
  public SplitPane getContent() {
    return ElementStorage.getElement(id);
  }

  /**
   * @see IElementActualizable
   */
  @Override
  public void handleBackgroundUpdates() {
    Platform.runLater(() -> {
      SplitPane splitPane = getContent();
      Button button = (Button) splitPane.getItems().get(0);

      if (LocalState.getConnectionEstablished()) {
        splitPane.getTooltip().setText(CONNECTION_STATUS_ESTABLISH_SUCCEEDED);
        button.setGraphic(ConnectionStatusImageCollection.getSuccessfulConnectionStatusImage(
                WindowHelper.getCircularElementSize(properties.getStatusImageScale())));
      } else {
        splitPane.getTooltip().setText(CONNECTION_STATUS_ESTABLISH_FAILED);
        button.setGraphic(ConnectionStatusImageCollection.getFailedConnectionStatusImage(
                WindowHelper.getCircularElementSize(properties.getStatusImageScale())));
      }
    });
//    Platform.runLater(() -> {
//      splitPane.setTooltip(new Tooltip("I'm the Tooltip Massage"));


//      getContent().getScene().setFill(Color.AQUA);
//      splitPane.setDisable(false);
//    });
//    ElementStorage.setElement(id, splitPane);

//    schedulerService.scheduleTask(
//        () -> {
//          Group connectionStatusImageView = getContent();
//          connectionStatusImageView.getChildren().removeAll();
//
//          if (LocalState.isConnectionEstablished()) {
//            connectionStatusImageView
//                .getChildren()
//                .add(ConnectionStatusImageCollection.getSuccessfulConnectionStatusImage());
//          } else {
//            connectionStatusImageView
//                .getChildren()
//                .add(ConnectionStatusImageCollection.getFailedConnectionStatusImage());
//          }
//        },
//        properties.getProcessBackgroundPeriod());
  }

  /**
   * @see IElementResizable
   */
  @Override
  public void handlePrefWidth() {
  }

  /**
   * @see IElementResizable
   */
  @Override
  public void handlePrefHeight() {
  }
}
