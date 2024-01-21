package com.resourcetracker.service.element.layout.scene.main.deployment.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.list.ListVisualizer;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.element.text.common.IElement;
import com.resourcetracker.service.element.text.common.IElementResizable;
import com.resourcetracker.service.event.state.LocalState;
import java.util.UUID;
import javafx.scene.layout.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents main deployment content grid used for state visualization. */
@Service
public class MainDeploymentContentGrid implements IElementResizable, IElement<GridPane> {
  UUID id = UUID.randomUUID();

  public MainDeploymentContentGrid(
      @Autowired PropertiesEntity properties,
      @Autowired MainDeploymentBarGrid mainDeploymentBarGrid,
      @Autowired ListVisualizer listVisualizer) {
    GridPane grid = new GridPane();
    grid.setVgap(properties.getCommonSceneContentVerticalGap());

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setHgrow(Priority.ALWAYS);

    grid.getColumnConstraints().add(column1);

    RowConstraints row1 = new RowConstraints();
    row1.setPercentHeight(9);
    RowConstraints row2 = new RowConstraints();
    row2.setPercentHeight(91);

    grid.getRowConstraints().addAll(row1, row2);

    grid.addColumn(0, mainDeploymentBarGrid.getContent(), listVisualizer.getContent());

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
    getContent().setMaxWidth(LocalState.getMainWindowWidth());
  }

  /**
   * @see IElementResizable
   */
  @Override
  public void handlePrefHeight() {
    getContent().setMaxHeight(LocalState.getMainWindowHeight());
  }
}
