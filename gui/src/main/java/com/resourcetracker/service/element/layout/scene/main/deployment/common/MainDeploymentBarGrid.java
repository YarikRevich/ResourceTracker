package com.resourcetracker.service.element.layout.scene.main.deployment.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.image.view.common.EditDeploymentConfigurationImageView;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.state.LocalState;
import java.util.UUID;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainDeploymentBarGrid implements IElementResizable, IElement<GridPane> {
  UUID id = UUID.randomUUID();

  public MainDeploymentBarGrid(
      @Autowired PropertiesEntity properties,
      @Autowired EditDeploymentConfigurationImageView editDeploymentConfigurationImageView) {
    GridPane grid = new GridPane();

    RowConstraints row1 = new RowConstraints();
    row1.setVgrow(Priority.ALWAYS);

    grid.getRowConstraints().add(row1);

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setHgrow(Priority.ALWAYS);
    column1.setPercentWidth(10);

    ColumnConstraints column2 = new ColumnConstraints();
    column2.setHgrow(Priority.ALWAYS);
    column2.setPercentWidth(10);

    ColumnConstraints column3 = new ColumnConstraints();
    column3.setHgrow(Priority.ALWAYS);
    column3.setPercentWidth(80);

    grid.getColumnConstraints().addAll(column1, column2, column3);

    grid.addColumn(0, editDeploymentConfigurationImageView.getContent());

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
    getContent().setPrefWidth(LocalState.getMainWindowWidth());
  }

  /**
   * @see IElementResizable
   */
  @Override
  public void handlePrefHeight() {
    getContent().setPrefHeight(LocalState.getMainWindowHeight());
  }
}
