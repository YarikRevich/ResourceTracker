package com.resourcetracker.service.element.layout.scene.main.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.alert.ApiServerNotAvailableAlert;
import com.resourcetracker.service.element.button.BasicButton;
import com.resourcetracker.service.element.common.ElementHelper;
import com.resourcetracker.service.element.progressbar.main.deployment.MainDeploymentCircleProgressBar;
import com.resourcetracker.service.element.progressbar.main.start.MainStartCircleProgressBar;
import com.resourcetracker.service.element.scene.main.deployment.MainDeploymentScene;
import com.resourcetracker.service.element.scene.main.start.MainStartScene;
import com.resourcetracker.service.element.stage.SettingsStage;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.state.LocalState;
import com.resourcetracker.service.scheduler.SchedulerHelper;
import java.util.UUID;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/** Represents common menu box. */
@Service
public class MainMenuButtonBox implements IElement<VBox> {
  UUID id = UUID.randomUUID();

  @Lazy @Autowired private MainStartScene startScene;

  @Lazy @Autowired private MainDeploymentScene deploymentScene;

  @Lazy @Autowired private ApiServerNotAvailableAlert apiServerNotAvailableAlert;

  public MainMenuButtonBox(
      @Autowired PropertiesEntity properties,
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
                    })
                .getContent(),
            new BasicButton(
                    "Deployment",
                    properties,
                    () -> {
                      if (LocalState.getConnectionEstablished()) {
                        ElementHelper.switchScene(
                            getContent().getScene(), deploymentScene.getContent());

                        ElementHelper.toggleElementVisibility(
                            mainDeploymentCircleProgressBar.getContent());

                        SchedulerHelper.scheduleTimer(
                            () ->
                                ElementHelper.toggleElementVisibility(
                                    mainDeploymentCircleProgressBar.getContent()),
                            properties.getSpinnerInitialDelay());
                      } else {

                        ElementHelper.showAlert(apiServerNotAvailableAlert.getContent());
                      }
                    })
                .getContent(),
            new BasicButton("Settings", properties, () -> settingsStage.getContent().show())
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
