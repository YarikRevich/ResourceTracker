package com.resourcetracker.service.element.image.view.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.ApplicationImageFileNotFoundException;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementActualizable;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.storage.ElementStorage;
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

/** Represents deployment refresh image. */
@Service
public class RefreshDeploymentStateImageView implements IElementResizable, IElement<BorderPane> {
  UUID id = UUID.randomUUID();

  private static final String REFRESH_DEPLOYMENT_DESCRIPTION = "refresh deployment state";

  public RefreshDeploymentStateImageView(@Autowired PropertiesEntity properties)
      throws ApplicationImageFileNotFoundException {
    Button button = new Button();

    InputStream imageSource =
        getClass().getClassLoader().getResourceAsStream(properties.getImageRefreshName());
    if (Objects.isNull(imageSource)) {
      throw new ApplicationImageFileNotFoundException();
    }

    ImageView imageView = new ImageView(new Image(imageSource));
    button.setGraphic(imageView);

    button.setAlignment(Pos.CENTER_RIGHT);

    SplitPane splitPane = new SplitPane(button);
    splitPane.setTooltip(new Tooltip(REFRESH_DEPLOYMENT_DESCRIPTION));

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
