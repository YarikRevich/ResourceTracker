package com.resourcetracker.service.element.scene.settings;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.layout.scene.settings.SettingsGeneralSceneLayout;
import com.resourcetracker.service.element.progressbar.settings.SettingsCircleProgressBar;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.element.text.common.IElement;
import java.util.UUID;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class SettingsGeneralScene implements IElement<Scene> {
  UUID id = UUID.randomUUID();

  public SettingsGeneralScene(
      @Autowired PropertiesEntity properties,
      @Lazy @Autowired SettingsGeneralSceneLayout settingsGeneralSceneLayout,
      @Autowired SettingsCircleProgressBar settingsCircleProgressBar) {
    Group group = new Group();
    group.getChildren().add(settingsGeneralSceneLayout.getContent());
    group.getChildren().add(settingsCircleProgressBar.getContent());

    ElementStorage.setElement(
        id,
        new Scene(
            group,
            Color.rgb(
                properties.getGeneralBackgroundColorR(),
                properties.getGeneralBackgroundColorG(),
                properties.getGeneralBackgroundColorB())));
  }

  public Scene getContent() {
    return ElementStorage.getElement(id);
  }
}
