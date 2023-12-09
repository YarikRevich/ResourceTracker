package com.resourcetracker.service.element.layout.buttonvbox;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElementResizable;
import java.util.UUID;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ButtonVBox implements IElementResizable<VBox> {
  UUID id = UUID.randomUUID();

  public ButtonVBox(PropertiesEntity properties, Button... buttons) {
    VBox vbox = new VBox(20, buttons);

    setElement(id, vbox);
  }

  /**
   * @return
   */
  @Override
  public VBox getContent() {
    return getElement(id);
  }

  /**
   * @param value window width value.
   */
  @Override
  public void prefWidth(Double value) {}

  /**
   * @param value window height value.
   */
  @Override
  public void prefHeight(Double value) {}
}
