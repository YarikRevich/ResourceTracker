package com.resourcetracker.service.element.stage;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.common.WindowHelper;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** SettingsStage represents settings window. */
@Service
public class SettingsStage {
  private static Stage settingsStage;

  public SettingsStage(@Autowired PropertiesEntity properties) {
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

          SettingsStage.settingsStage = settingsStage;
        });
  }

  public Stage getContent() {
    return settingsStage;
  }
}
