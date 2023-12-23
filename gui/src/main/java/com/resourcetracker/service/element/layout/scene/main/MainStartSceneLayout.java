package com.resourcetracker.service.element.layout.scene.main;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.layout.common.ContentGrid;
import com.resourcetracker.service.element.layout.scene.main.components.FooterGrid;
import com.resourcetracker.service.element.layout.scene.main.components.HeaderGrid;
import com.resourcetracker.service.element.layout.scene.main.components.MainMenuButtonBox;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.element.text.LandingAnnouncementText;
import com.resourcetracker.service.event.LocalState;
import java.util.UUID;
import javafx.scene.layout.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents start scene layout of the main stage. */
@Service
public class MainStartSceneLayout implements IElementResizable, IElement<GridPane> {
  UUID id = UUID.randomUUID();

  public MainStartSceneLayout(
      @Autowired PropertiesEntity properties,
      @Autowired MainMenuButtonBox mainMenuButtonBox,
      @Autowired LandingAnnouncementText landingAnnouncementText,
      @Autowired HeaderGrid headerGrid,
      @Autowired FooterGrid footerGrid) {
    GridPane grid = new GridPane();

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setHgrow(Priority.ALWAYS);

    grid.getColumnConstraints().add(column1);

    RowConstraints row1 = new RowConstraints();
    row1.setPercentHeight(5);
    RowConstraints row2 = new RowConstraints();
    row2.setVgrow(Priority.ALWAYS);
    row2.setPercentHeight(86);
    RowConstraints row3 = new RowConstraints();
    row3.setPercentHeight(4);

    grid.getRowConstraints().addAll(row1, row2, row3);

    ContentGrid contentGrid =
        new ContentGrid(mainMenuButtonBox.getContent(), landingAnnouncementText.getContent());

    grid.addColumn(0, headerGrid.getContent(), contentGrid.getContent(), footerGrid.getContent());

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
