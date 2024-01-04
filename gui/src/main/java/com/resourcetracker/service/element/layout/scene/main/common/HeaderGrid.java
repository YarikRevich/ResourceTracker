package com.resourcetracker.service.element.layout.scene.main.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.image.view.ConnectionStatusImageView;
import com.resourcetracker.service.element.storage.ElementStorage;
import java.util.UUID;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** */
@Service
public class HeaderGrid implements IElement<GridPane> {
  UUID id = UUID.randomUUID();

  public HeaderGrid(
      @Autowired PropertiesEntity properties,
      @Autowired ConnectionStatusImageView connectionStatusImageView) {
    GridPane grid = new GridPane();
    grid.setBackground(
        Background.fill(
            Color.rgb(
                properties.getCommonSceneHeaderBackgroundColorR(),
                properties.getCommonSceneHeaderBackgroundColorG(),
                properties.getCommonSceneHeaderBackgroundColorB())));

    RowConstraints row1 = new RowConstraints();
    row1.setVgrow(Priority.ALWAYS);

    grid.getRowConstraints().add(0, row1);

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setHgrow(Priority.ALWAYS);
    column1.setPercentWidth(80);

    ColumnConstraints column2 = new ColumnConstraints();
    column2.setHgrow(Priority.ALWAYS);
    column2.setPercentWidth(20);

    grid.getColumnConstraints().add(0, column1);
    grid.getColumnConstraints().add(1, column2);

    grid.addColumn(1, connectionStatusImageView.getContent());

    ElementStorage.setElement(id, grid);
  }

  /**
   * @see IElement
   */
  @Override
  public GridPane getContent() {
    return ElementStorage.getElement(id);
  }
}
