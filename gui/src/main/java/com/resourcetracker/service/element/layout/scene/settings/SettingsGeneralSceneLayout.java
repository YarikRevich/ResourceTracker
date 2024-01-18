package com.resourcetracker.service.element.layout.scene.settings;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.layout.common.ContentGrid;
import com.resourcetracker.service.element.layout.scene.settings.common.SettingsMenuButtonBox;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.element.text.LandingAnnouncementText;
import com.resourcetracker.service.event.state.LocalState;
import java.util.UUID;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Represents general settings layout of the settings stage. */
@Component
public class SettingsGeneralSceneLayout implements IElementResizable, IElement<GridPane> {
  UUID id = UUID.randomUUID();

  public SettingsGeneralSceneLayout(
      @Autowired PropertiesEntity properties,
      @Autowired SettingsMenuButtonBox settingsMenuButtonBox,
      @Autowired LandingAnnouncementText landingAnnouncementText) {
    GridPane grid = new GridPane();

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setHgrow(Priority.ALWAYS);

    grid.getColumnConstraints().add(column1);

    ContentGrid contentGrid =
        new ContentGrid(settingsMenuButtonBox.getContent(), landingAnnouncementText.getContent());

    RowConstraints row1 = new RowConstraints();
    row1.setPercentHeight(95);

    grid.getRowConstraints().addAll(row1);

    grid.addColumn(0, contentGrid.getContent());

    ElementStorage.setElement(id, grid);
    ElementStorage.setResizable(this);
  }

  /**
   * @see IElement
   */
  @Override
  public GridPane getContent() {
    return ElementStorage.getElement(id);
  }

  /**
   * @see IElementResizable
   */
  @Override
  public void handlePrefWidth() {
    getContent().setMinWidth(LocalState.getMainWindowWidth());
  }

  /**
   * @see IElementResizable
   */
  @Override
  public void handlePrefHeight() {
    getContent().setMinHeight(LocalState.getMainWindowHeight());
  }
}
