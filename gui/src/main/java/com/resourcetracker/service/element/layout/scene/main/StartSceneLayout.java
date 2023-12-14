package com.resourcetracker.service.element.layout.scene.main;

import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.layout.common.ContentGrid;
import com.resourcetracker.service.element.layout.common.FooterGrid;
import com.resourcetracker.service.element.layout.common.HeaderGrid;
import com.resourcetracker.service.element.layout.common.MenuButtonBox;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.element.text.LandingAnnouncementText;
import com.resourcetracker.service.event.state.LocalState;
import java.util.UUID;
import javafx.scene.layout.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartSceneLayout implements IElementResizable, IElement<GridPane> {
  UUID id = UUID.randomUUID();

  public StartSceneLayout(
      @Autowired MenuButtonBox menuButtonVBox,
      @Autowired LandingAnnouncementText landingAnnouncementText,
      @Autowired HeaderGrid headerGrid,
      @Autowired FooterGrid footerGrid) {
    GridPane grid = new GridPane();
    grid.setGridLinesVisible(true);

    ContentGrid<LandingAnnouncementText> contentGrid =
        new ContentGrid<>(menuButtonVBox, landingAnnouncementText);

    ColumnConstraints column1 = new ColumnConstraints();
    //    column1.setHgrow(Priority.ALWAYS);

    grid.getColumnConstraints().add(0, column1);

    RowConstraints row1 = new RowConstraints();
    //    row1.setPercentHeight(5);
    RowConstraints row2 = new RowConstraints();
    row2.setVgrow(Priority.ALWAYS);
    //    row2.setPercentHeight(10);
    RowConstraints row3 = new RowConstraints();
    //    row3.setVgrow(Priority.ALWAYS);
    //    row3.setPercentHeight(20);

    grid.getRowConstraints().add(0, row1);
    grid.getRowConstraints().add(1, row2);
    grid.getRowConstraints().add(2, row3);

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
    getContent().setMinWidth(LocalState.getWindowWidth());
  }

  /**
   * @see IElementResizable
   */
  @Override
  public void handlePrefHeight() {
    getContent().setMinHeight(LocalState.getWindowHeight());
  }
}
