package com.resourcetracker.service.element.list.cell;

import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.storage.ElementStorage;
import java.util.UUID;
import javafx.scene.layout.HBox;

/**
 * Represents list visualizer cell, which resolves access for more information to get the details.
 */
public class ListVisualizerCell implements IElement<HBox> {
  UUID id = UUID.randomUUID();

  public ListVisualizerCell(String text, String redirectionButtonImage) {
    HBox hbox = new HBox();
    //
    //        label.setText(labelText);
    //        label.setMaxWidth(Double.MAX_VALUE);
    //        HBox.setHgrow(label, Priority.ALWAYS);
    //
    //        button.setText(buttonText);

    ElementStorage.setElement(id, hbox);
  }

  /**
   * @see IElement
   */
  @Override
  public HBox getContent() {
    return ElementStorage.getElement(id);
  }
}
