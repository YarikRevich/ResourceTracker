package com.resourcetracker.service.element.layout.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import java.util.UUID;

import com.resourcetracker.service.element.storage.ElementStorage;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ButtonVBox implements IElementResizable, IElement<VBox> {
  UUID id = UUID.randomUUID();

  public ButtonVBox(Button... buttons) {
    VBox vbox = new VBox(20, buttons);
    vbox.setFillWidth(true);

    ElementStorage.setElement(id, vbox);
  }

  /**
   * @return
   */
  @Override
  public VBox getContent() {
    return ElementStorage.getElement(id);
  }

  /**
   *
   */
  @Override
  public void handlePrefWidth() {

  }

  /**
   *
   */
  @Override
  public void handlePrefHeight() {

  }
}
