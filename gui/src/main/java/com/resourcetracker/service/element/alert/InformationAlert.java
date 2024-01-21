package com.resourcetracker.service.element.alert;

import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.element.text.common.IElement;
import ink.bluecloud.css.CssResources;
import ink.bluecloud.css.ElementButton;
import ink.bluecloud.css.ElementButtonKt;
import java.util.UUID;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Service;

/** Represents error related alert. */
@Service
public class InformationAlert implements IElement<Alert> {
  UUID id = UUID.randomUUID();

  public InformationAlert() {
    Platform.runLater(
        () -> {
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("Action information");

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
