package com.resourcetracker.service.element.layout.scene.main.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.alert.ErrorAlert;
import com.resourcetracker.service.element.button.BasicButton;
import com.resourcetracker.service.element.common.ElementHelper;
import com.resourcetracker.service.element.progressbar.main.deployment.MainDeploymentCircleProgressBar;
import com.resourcetracker.service.element.progressbar.main.start.MainStartCircleProgressBar;
import com.resourcetracker.service.element.scene.main.deployment.MainDeploymentScene;
import com.resourcetracker.service.element.scene.main.start.MainStartScene;
import com.resourcetracker.service.element.stage.SettingsStage;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.element.text.common.IElement;
import com.resourcetracker.service.event.payload.DeploymentStateRetrievalEvent;
import com.resourcetracker.service.event.state.LocalState;
import com.resourcetracker.service.scheduler.SchedulerHelper;
import java.util.UUID;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/** Represents common menu box. */
@Service
public class MainMenuButtonBox implements IElement<VBox> {
  UUID id = UUID.randomUUID();

  @Lazy @Autowired private MainStartScene startScene;

  @Lazy @Autowired private MainDeploymentScene deploymentScene;

  @Lazy @Autowired private ErrorAlert errorAlert;

  public MainMenuButtonBox(
      @Autowired PropertiesEntity properties,
      @Autowired ApplicationEventPublisher applicationEventPublisher,
      @Autowired SettingsStage settingsStage,
      @Autowired MainStartCircleProgressBar mainStartCircleProgressBar,
      @Autowired MainDeploymentCircleProgressBar mainDeploymentCircleProgressBar) {
    VBox vbox =
        new VBox(
            20,
            new BasicButton(
                    "Start",
                    properties,
                    () -> {
                      ElementHelper.switchScene(getContent().getScene(), startScene.getContent());

                      ElementHelper.toggleElementVisibility(
                          mainStartCircleProgressBar.getContent());

                      SchedulerHelper.scheduleTimer(
                          () ->
                              ElementHelper.toggleElementVisibility(
                                  mainStartCircleProgressBar.getContent()),
                          properties.getSpinnerInitialDelay());
                    })
                .getContent(),
            new BasicButton(
                    "Deployment",
                    properties,
                    () -> {
                      if (LocalState.getConnectionEstablished()) {
                        if (!ElementHelper.areElementsEqual(
                            getContent().getScene(), deploymentScene.getContent())) {
                          ElementHelper.switchScene(
                              getContent().getScene(), deploymentScene.getContent());

                          applicationEventPublisher.publishEvent(
                              new DeploymentStateRetrievalEvent());
                        }
                      } else {
                        ElementHelper.showAlert(
                            errorAlert.getContent(),
                            properties.getAlertApiServerUnavailableMessage());
                      }
                    })
                .getContent());
    vbox.setPadding(new Insets(10, 0, 10, 0));
    vbox.setAlignment(Pos.TOP_CENTER);
    vbox.setFillWidth(true);

    vbox.setStyle(
        String.format(
            "-fx-background-color: rgb(%d, %d, %d); " + "-fx-background-radius: 10;",
            properties.getCommonSceneMenuBackgroundColorR(),
            properties.getCommonSceneMenuBackgroundColorG(),
            properties.getCommonSceneMenuBackgroundColorB()));

    ElementStorage.setElement(id, vbox);
  }

  /**
   * @see IElement
   */
  @Override
  public VBox getContent() {
    return ElementStorage.getElement(id);
  }
}
