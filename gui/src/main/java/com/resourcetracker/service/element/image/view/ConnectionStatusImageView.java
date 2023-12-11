package com.resourcetracker.service.element.image.view;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementActualizable;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.common.WindowHelper;
import com.resourcetracker.service.element.image.collection.ConnectionStatusImageCollection;

import java.util.UUID;

import com.resourcetracker.service.element.storage.ElementStorage;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents connection status image. */
@Service
public class ConnectionStatusImageView implements IElementActualizable, IElementResizable, IElement<SplitPane> {
  UUID id = UUID.randomUUID();

  public ConnectionStatusImageView(@Autowired PropertiesEntity properties) {
    Button connectionStatusImageView = new Button();
    connectionStatusImageView.setDisable(true);
    SplitPane splitPane = new SplitPane(connectionStatusImageView);
    splitPane.setTooltip(new Tooltip("I'm the Tooltip Massage"));
//
//    Button connectionStatusImageView = new Button();
//    connectionStatusImageView.setDisable(false);
//    connectionStatusImageView.setTooltip(new Tooltip("IT WORKS!"));

    connectionStatusImageView.setGraphic(ConnectionStatusImageCollection.getFailedConnectionStatusImage(
            WindowHelper.getCircularElementSize(properties.getStatusImageScale())));
//    connectionStatusImageView.(ConnectionStatusImageCollection.getFailedConnectionStatusImage(
//            WindowHelper.getCircularElementSize(properties.getStatusImageScale())));
//
//    connectionStatusImageView
//        .getChildren()
//        .add();

    ElementStorage.setElement(id, splitPane);
    ElementStorage.setActualizable(this);
    ElementStorage.setResizable(this);
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
