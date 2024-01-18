package com.resourcetracker.service.element.alert;

import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.storage.ElementStorage;
import ink.bluecloud.css.CssResources;
import ink.bluecloud.css.ElementButton;
import ink.bluecloud.css.ElementButtonKt;
import java.util.UUID;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Service;

/** Represents alert about API Server unavailability. */
@Service
public class ApiServerNotAvailableAlert implements IElement<Alert> {
  UUID id = UUID.randomUUID();

  public ApiServerNotAvailableAlert() {
    Platform.runLater(
        () -> {
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Action cannot be performed");
          alert.setContentText("API Server connection was not established");

          ElementButtonKt.theme(
              (Button) alert.getDialogPane().lookupButton(ButtonType.OK), ElementButton.redButton);
          alert.getDialogPane().getStylesheets().add(CssResources.globalCssFile);
          alert.getDialogPane().getStylesheets().add(CssResources.buttonCssFile);
          alert.getDialogPane().getStylesheets().add(CssResources.textFieldCssFile);

          ElementStorage.setElement(id, alert);
        });
  }

  /**
   * @see IElement
   */
  @Override
  public Alert getContent() {
    return ElementStorage.getElement(id);
  }
}
