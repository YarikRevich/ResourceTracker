
package com.resourcetracker.service.element.button;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.common.ElementHelper;
import ink.bluecloud.css.CssResources;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import ink.bluecloud.css.ElementButton;
import ink.bluecloud.css.ElementButtonKt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 *
 */
public class BasicButton implements IElement<Button> {
    UUID id = UUID.randomUUID();

    /**
     * Default constructor used for initialization purpose. Does
     * not require to be scheduled, because it supposed to be
     * created in already scheduled process.
     * @param properties properties used for application configuration
     *                   purposes.
     */
    public BasicButton(String text, PropertiesEntity properties, Runnable action) {
        Button basicButton = new Button();

        Rectangle2D button = ElementHelper.getSizeWithScale(
                properties.getBasicButtonSizeWidth(),
                properties.getBasicButtonSizeHeight());

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
}
