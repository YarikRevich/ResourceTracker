package com.resourcetracker.service.element.layout.common;

import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.state.LocalState;
import java.util.UUID;
import javafx.scene.Node;
import javafx.scene.layout.*;

public class ContentGrid<K extends IElement<?>> implements IElementResizable, IElement<GridPane> {
  UUID id = UUID.randomUUID();

  public ContentGrid(MenuButtonBox buttons, K element) {
    GridPane grid = new GridPane();
    grid.setGridLinesVisible(true);

    RowConstraints row1 = new RowConstraints();
    row1.setVgrow(Priority.ALWAYS);

    grid.getRowConstraints().add(0, row1);

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setPercentWidth(30);
    ColumnConstraints column2 = new ColumnConstraints();
    column2.setPercentWidth(70);

    grid.getColumnConstraints().add(0, column1);
    grid.getColumnConstraints().add(1, column2);

    grid.addColumn(0, buttons.getContent());
    grid.addColumn(1, (Node) element.getContent());

    ElementStorage.setElement(id, grid);
    ElementStorage.setResizable(this);

    ElementStorage.setElement(id, grid);
    ElementStorage.setResizable(this);
  }

  /**
   * @return
   */
  @Override
  public GridPane getContent() {
    return ElementStorage.getElement(id);
  }

  /** */
  @Override
  public void handlePrefWidth() {
    getContent().setMinWidth(LocalState.getWindowWidth());
  }

  /** */
  @Override
  public void handlePrefHeight() {
    getContent().setMinWidth(LocalState.getWindowWidth());
  }
}
