package com.resourcetracker.service.element.layout.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import java.util.UUID;

import com.resourcetracker.service.element.button.BasicButton;
import com.resourcetracker.service.element.common.WindowHelper;
import com.resourcetracker.service.element.progressbar.CircleProgressBar;
import com.resourcetracker.service.element.scene.main.DeploymentScene;
import com.resourcetracker.service.element.scene.main.StartScene;
import com.resourcetracker.service.element.stage.SettingsStage;
import com.resourcetracker.service.element.storage.ElementStorage;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class MenuButtonBox implements IElementResizable, IElement<VBox> {
  UUID id = UUID.randomUUID();

  @Lazy
  @Autowired
  private StartScene startScene;

  @Lazy
  @Autowired
  private DeploymentScene deploymentScene;

  public MenuButtonBox(
          @Autowired PropertiesEntity properties,
          @Autowired SettingsStage settingsStage,
          @Autowired CircleProgressBar circleProgressBar
          ) {
    VBox vbox = new VBox(
                20,
                    new BasicButton(
                            "Start",
                            properties,
                            () -> {
                              WindowHelper.toggleElementVisibility(circleProgressBar.getContent());
                              WindowHelper.switchScene(getContent().getScene(), startScene.getContent());
                            })
                            .getContent(),
                    new BasicButton(
                            "Deployment",
                            properties,
                            () -> WindowHelper.switchScene(getContent().getScene(), deploymentScene.getContent()))
                            .getContent(),
                    new BasicButton(
                            "Settings",
                            properties,
                            () -> settingsStage.getContent().show())
                            .getContent());
    vbox.setAlignment(Pos.TOP_CENTER);
    vbox.setFillWidth(true);

    //        VBox.setMargin(buttonVBox.getContent(), new Insets(100, 100, 100, 100));

    ElementStorage.setElement(id, vbox);
  }

  /**
   * @return
   */
  @Override
  public VBox getContent() {
    return ElementStorage.getElement(id);
  }

  /**
   *
   */
  @Override
  public void handlePrefWidth() {

  }

  /**
   *
   */
  @Override
  public void handlePrefHeight() {

  }
}
