package com.resourcetracker.service.element.button;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.common.ElementHelper;
import ink.bluecloud.css.CssResources;
import ink.bluecloud.css.ElementButton;
import ink.bluecloud.css.ElementButtonKt;
import java.util.UUID;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;

/** */
public class BasicButton implements IElementResizable<Button> {
  UUID id = UUID.randomUUID();

  /**
   * Default constructor used for initialization purpose. Does not require to be scheduled, because
   * it supposed to be created in already scheduled process.
   *
   * @param properties properties used for application configuration purposes.
   */
  public BasicButton(String text, PropertiesEntity properties, Runnable action) {
    Button basicButton = new Button();

    Rectangle2D button =
        ElementHelper.getSizeWithScale(
            properties.getBasicButtonSizeWidth(), properties.getBasicButtonSizeHeight());

    //    basicButton.prefWidthProperty().bind(Bindings.divide(basicButton.prefWidthProperty(),
    // 10));
    //    basicButton.heightProperty().addListener((InvalidationListener) observable -> {
    //        System.out.println("window updated");
    ////      updateWindowHeight(rootPane);
    //    });

    basicButton.setPrefWidth(button.getWidth());
    basicButton.setPrefHeight(button.getHeight());

    ElementButtonKt.theme(basicButton, ElementButton.greenButton);
    basicButton.getStylesheets().add(CssResources.globalCssFile);
    basicButton.getStylesheets().add(CssResources.buttonCssFile);
    basicButton.getStylesheets().add(CssResources.textFieldCssFile);

    basicButton.setText(text);
    basicButton.setOnAction(event -> action.run());

    setElement(id, basicButton);
  }

  /**
   * @return
   */
  @Override
  public Button getContent() {
    return getElement(id);
  }

  /**
   * @param value window width value.
   */
  @Override
  public void prefWidth(Double value) {
    System.out.println(value);
  }

  /**
   * @param value window height value.
   */
  @Override
  public void prefHeight(Double value) {
    System.out.println(value);
  }
}
