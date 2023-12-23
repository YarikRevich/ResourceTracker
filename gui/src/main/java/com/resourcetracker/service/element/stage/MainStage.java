package com.resourcetracker.service.element.stage;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.common.ElementHelper;
import com.resourcetracker.service.element.progressbar.stage.main.MainCircleProgressBar;
import com.resourcetracker.service.element.scene.main.MainStartScene;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.payload.MainWindowHeightUpdateEvent;
import com.resourcetracker.service.event.payload.MainWindowWidthUpdateEvent;
import com.resourcetracker.service.scheduler.SchedulerHelper;
import java.util.UUID;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
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
      @Autowired MainStartScene startScene,
      @Autowired MainCircleProgressBar mainCircleProgressBar,
      @Autowired ApplicationEventPublisher applicationEventPublisher) {
    Platform.runLater(
        () -> {
          Stage mainStage = new Stage();
          mainStage.setTitle(properties.getWindowMainName());

          Rectangle2D defaultBounds = Screen.getPrimary().getVisualBounds();

          Rectangle2D windowMin =
              ElementHelper.getSizeWithScale(
                  defaultBounds.getWidth(),
                  defaultBounds.getHeight(),
                  properties.getWindowMainScaleMinWidth(),
                  properties.getWindowMainScaleMinHeight());

          mainStage.setWidth(windowMin.getWidth());
          mainStage.setHeight(windowMin.getHeight());
          mainStage.setMinWidth(windowMin.getWidth());
          mainStage.setMinHeight(windowMin.getHeight());

          Rectangle2D windowMax =
              ElementHelper.getSizeWithScale(
                  defaultBounds.getWidth(),
                  defaultBounds.getHeight(),
                  properties.getWindowMainScaleMaxWidth(),
                  properties.getWindowMainScaleMaxHeight());

          mainStage.setMaxWidth(windowMax.getWidth());
          mainStage.setMaxHeight(windowMax.getHeight());

          Point2D centralPoint =
              ElementHelper.getCentralPoint(mainStage.getWidth(), mainStage.getHeight());
          mainStage.setX(centralPoint.getX());
          mainStage.setY(centralPoint.getY());

          mainStage.setScene(startScene.getContent());

          mainStage
              .widthProperty()
              .addListener(
                  (obs, oldVal, newVal) -> {
                    applicationEventPublisher.publishEvent(
                        new MainWindowWidthUpdateEvent(newVal.doubleValue()));
                  });
          mainStage
              .heightProperty()
              .addListener(
                  (obs, oldVal, newVal) -> {
                    applicationEventPublisher.publishEvent(
                        new MainWindowHeightUpdateEvent(newVal.doubleValue()));
                  });

          mainStage.setOnShown(
              event -> {
                ElementHelper.toggleElementVisibility(mainCircleProgressBar.getContent());

                SchedulerHelper.scheduleTimer(
                    () -> ElementHelper.toggleElementVisibility(mainCircleProgressBar.getContent()),
                    properties.getSpinnerInitialDelay());
              });

          ElementStorage.setElement(id, mainStage);
        });
  }

  public Stage getContent() {
    return ElementStorage.getElement(id);
  }
}
