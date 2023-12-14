package com.resourcetracker.service.element.stage;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.common.WindowHelper;
import com.resourcetracker.service.element.menu.TabMenuBar;
import com.resourcetracker.service.element.storage.ElementStorage;
import java.util.UUID;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** SettingsStage represents settings window. */
@Service
public class SettingsStage implements IElement<Stage> {
  UUID id = UUID.randomUUID();

  public SettingsStage(@Autowired PropertiesEntity properties, @Autowired TabMenuBar tabMenuBar) {
    Platform.runLater(
        () -> {
          Stage settingsStage = new Stage();
          settingsStage.setTitle(properties.getWindowSettingsName());

          Rectangle2D defaultBounds = Screen.getPrimary().getVisualBounds();

          Rectangle2D window =
              WindowHelper.getSizeWithScale(
                  defaultBounds.getWidth(),
                  defaultBounds.getHeight(),
                  properties.getWindowSettingsScaleWidth(),
                  properties.getWindowSettingsScaleHeight());
          settingsStage.setWidth(window.getWidth());
          settingsStage.setHeight(window.getHeight());

          Point2D centralPoint =
              WindowHelper.getCentralPoint(settingsStage.getWidth(), settingsStage.getHeight());
          settingsStage.setX(centralPoint.getX());
          settingsStage.setY(centralPoint.getY());

          ElementStorage.setElement(id, settingsStage);
        });
  }

  public Stage getContent() {
    return ElementStorage.getElement(id);
  }
}
