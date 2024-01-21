package com.resourcetracker.service.element.layout.scene.settings.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.button.BasicButton;
import com.resourcetracker.service.element.common.ElementHelper;
import com.resourcetracker.service.element.scene.main.start.MainStartScene;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.element.text.common.IElement;
import java.util.UUID;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/** Represents common menu box. */
@Service
public class SettingsMenuButtonBox implements IElement<VBox> {
  UUID id = UUID.randomUUID();

  @Lazy @Autowired private MainStartScene startScene;

  public SettingsMenuButtonBox(@Autowired PropertiesEntity properties) {
    VBox vbox =
        new VBox(
            20,
            new BasicButton(
                    "General",
                    properties,
                    () -> {
                      ElementHelper.switchScene(getContent().getScene(), startScene.getContent());
                    })
                .getContent(),
            new BasicButton("API Server", properties, () -> {}).getContent());
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
