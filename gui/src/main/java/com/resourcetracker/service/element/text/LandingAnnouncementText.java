package com.resourcetracker.service.element.text;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.font.FontLoader;
import com.resourcetracker.service.element.storage.ElementStorage;
import java.util.UUID;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandingAnnouncementText implements IElement<Label> {
  UUID id = UUID.randomUUID();

  public LandingAnnouncementText(@Autowired PropertiesEntity properties) {
    Label label =
        new Label(
            "Welcome to ResourceTracker GUI!\n"
                + "This application was developed to interact with API Server");
    label.setFont(FontLoader.getFont20());
    label.setAlignment(Pos.TOP_CENTER);
    label.setTextAlignment(TextAlignment.CENTER);
    //    label.setMaxWidth(MAX_VALUE);
    label.setWrapText(true);

    label.setStyle(
        String.format(
            "-fx-background-color: rgb(%d, %d, %d); " + "-fx-background-radius: 10;",
            properties.getCommonSceneContentBackgroundColorR(),
            properties.getCommonSceneContentBackgroundColorG(),
            properties.getCommonSceneContentBackgroundColorB()));

    ElementStorage.setElement(id, label);
  }

  /**
   * @see IElement
   */
  @Override
  public Label getContent() {
    return ElementStorage.getElement(id);
  }
}
