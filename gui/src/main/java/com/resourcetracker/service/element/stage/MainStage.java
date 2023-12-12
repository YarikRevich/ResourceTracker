package com.resourcetracker.service.element.stage;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.common.WindowHelper;
import com.resourcetracker.service.element.scene.main.StartScene;
import java.util.UUID;

import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.state.payload.WindowHeightUpdateEvent;
import com.resourcetracker.service.event.state.payload.WindowWidthUpdateEvent;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/** MainStage represents main window. */
@Service
public class MainStage implements IElement<Stage> {
  UUID id = UUID.randomUUID();

  public MainStage(
      @Autowired PropertiesEntity properties,
      @Autowired StartScene startScene,
      @Autowired ApplicationEventPublisher applicationEventPublisher) {
    Platform.runLater(
        () -> {
          Stage mainStage = new Stage();
          mainStage.setTitle(properties.getWindowMainName());

          Rectangle2D window =
              WindowHelper.getSizeWithScale(
                  properties.getWindowMainScaleWidth(), properties.getWindowMainScaleHeight());
          mainStage.setWidth(window.getWidth());
          mainStage.setHeight(window.getHeight());
          mainStage.setMinWidth(window.getWidth());
          mainStage.setMinHeight(window.getHeight());

          Point2D centralPoint =
              WindowHelper.getCentralPoint(mainStage.getWidth(), mainStage.getHeight());
          mainStage.setX(centralPoint.getX());
          mainStage.setY(centralPoint.getY());

          mainStage.setScene(startScene.getContent());

          mainStage
              .widthProperty()
              .addListener(
                  (obs, oldVal, newVal) -> applicationEventPublisher.publishEvent(
                          new WindowWidthUpdateEvent(newVal.doubleValue())));
          mainStage
              .heightProperty()
              .addListener(
                  (obs, oldVal, newVal) -> applicationEventPublisher.publishEvent(
                          new WindowHeightUpdateEvent(newVal.doubleValue())));

            ElementStorage.setElement(id, mainStage);
        });
  }

  public Stage getContent() {
    return ElementStorage.getElement(id);
  }
}
