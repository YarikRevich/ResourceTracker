package com.resourcetracker.service.element.image.view.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.ApplicationImageFileNotFoundException;
import com.resourcetracker.exception.CommandExecutorException;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementActualizable;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.hand.config.command.OpenConfigEditorCommandService;
import com.resourcetracker.service.hand.executor.CommandExecutorService;
import ink.bluecloud.css.CssResources;
import ink.bluecloud.css.ElementButton;
import ink.bluecloud.css.ElementButtonKt;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents deployment edit configuration image. */
@Service
public class EditDeploymentConfigurationImageView
    implements IElementResizable, IElement<BorderPane> {
  UUID id = UUID.randomUUID();

  private static final String EDIT_DEPLOYMENT_CONFIGURATION_DESCRIPTION =
      "edit deployment configuration file";

  public EditDeploymentConfigurationImageView(
      @Autowired PropertiesEntity properties,
      @Autowired CommandExecutorService commandExecutorService)
      throws ApplicationImageFileNotFoundException {
    Button button = new Button();
    ElementButtonKt.theme(button, ElementButton.redButton);
    button.getStylesheets().add(CssResources.globalCssFile);
    button.getStylesheets().add(CssResources.buttonCssFile);
    button.getStylesheets().add(CssResources.textFieldCssFile);
    button.getStylesheets().add("-fx-focus-color: transparent;");

    button.setOnAction(
        event -> {
          OpenConfigEditorCommandService openConfigEditorCommandService =
              new OpenConfigEditorCommandService(
                  properties.getConfigRootPath(), properties.getConfigUserFilePath());

          try {
            commandExecutorService.executeCommand(openConfigEditorCommandService);
          } catch (CommandExecutorException e) {
            throw new RuntimeException(e);
          }
        });

    InputStream imageSource =
        getClass().getClassLoader().getResourceAsStream(properties.getImageEditName());
    if (Objects.isNull(imageSource)) {
      throw new ApplicationImageFileNotFoundException();
    }

    ImageView imageView = new ImageView(new Image(imageSource));
    button.setGraphic(imageView);

    button.setAlignment(Pos.CENTER_RIGHT);

    SplitPane splitPane = new SplitPane(button);
    splitPane.setTooltip(new Tooltip(EDIT_DEPLOYMENT_CONFIGURATION_DESCRIPTION));

    BorderPane borderPane = new BorderPane();
    borderPane.setRight(splitPane);

    ElementStorage.setElement(id, borderPane);
    ElementStorage.setResizable(this);
  }

  /**
   * @see IElementActualizable
   */
  @Override
  public BorderPane getContent() {
    return ElementStorage.getElement(id);
  }

  /**
   * @see IElementResizable
   */
  @Override
  public void handlePrefWidth() {}

  /**
   * @see IElementResizable
   */
  @Override
  public void handlePrefHeight() {}
}
