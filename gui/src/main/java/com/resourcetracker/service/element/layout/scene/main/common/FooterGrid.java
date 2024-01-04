package com.resourcetracker.service.element.layout.scene.main.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.element.text.BuildVersionText;
import java.util.UUID;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Represents common footer used in all the scenes. */
@Service
public class FooterGrid implements IElement<GridPane> {
  UUID id = UUID.randomUUID();

  public FooterGrid(
      @Autowired PropertiesEntity properties, @Autowired BuildVersionText buildVersionText) {
    GridPane grid = new GridPane();
    grid.setBackground(
        Background.fill(
            Color.rgb(
                properties.getCommonSceneFooterBackgroundColorR(),
                properties.getCommonSceneFooterBackgroundColorG(),
                properties.getCommonSceneFooterBackgroundColorB())));

    RowConstraints row1 = new RowConstraints();
    row1.setVgrow(Priority.ALWAYS);

    grid.getRowConstraints().add(row1);

    ColumnConstraints column1 = new ColumnConstraints();
    column1.setHgrow(Priority.ALWAYS);
    column1.setPercentWidth(30);

    ColumnConstraints column2 = new ColumnConstraints();
    column2.setHgrow(Priority.ALWAYS);
    column2.setPercentWidth(70);

    grid.getColumnConstraints().addAll(column1, column2);

    grid.addColumn(0, buildVersionText.getContent());

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
