package com.resourcetracker.service.element.stage;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.common.WindowHelper;
import com.resourcetracker.service.element.progressbar.CircleProgressBar;
import com.resourcetracker.service.element.scene.main.StartScene;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.state.payload.WindowHeightUpdateEvent;
import com.resourcetracker.service.event.state.payload.WindowWidthUpdateEvent;
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
      @Autowired StartScene startScene,
      @Autowired CircleProgressBar circleProgressBar,
      @Autowired ApplicationEventPublisher applicationEventPublisher) {
    Platform.runLater(
        () -> {
          Stage mainStage = new Stage();
          mainStage.setTitle(properties.getWindowMainName());

          Rectangle2D defaultBounds = Screen.getPrimary().getVisualBounds();

          Rectangle2D window =
              WindowHelper.getSizeWithScale(
                  defaultBounds.getWidth(),
                  defaultBounds.getHeight(),
                  properties.getWindowMainScaleWidth(),
                  properties.getWindowMainScaleHeight());

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
                  (obs, oldVal, newVal) -> {
                    applicationEventPublisher.publishEvent(
                        new WindowWidthUpdateEvent(newVal.doubleValue()));
                  });
          mainStage
              .heightProperty()
              .addListener(
                  (obs, oldVal, newVal) -> {
                    applicationEventPublisher.publishEvent(
                        new WindowHeightUpdateEvent(newVal.doubleValue()));
                  });

          SchedulerHelper.scheduleTimer(
              () -> WindowHelper.toggleElementVisibility(circleProgressBar.getContent()),
              properties.getSpinnerInitialDelay());

          ElementStorage.setElement(id, mainStage);
        });
  }

  public Stage getContent() {
    return ElementStorage.getElement(id);
  }
}
