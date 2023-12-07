package com.resourcetracker.service.element.button;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.common.ElementHelper;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import ink.bluecloud.css.ElementButton;
import ink.bluecloud.css.ElementButtonKt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class BasicButton implements IElement<Button> {
    private static Button basicButton;

    /**
     * Default constructor used for initialization purpose. Does
     * not require to be scheduled, because it supposed to be
     * created in already scheduled process.
     * @param properties properties used for application configuration
     *                   purposes.
     */
    public BasicButton(@Autowired PropertiesEntity properties) {
        Button basicButton = new Button();

        Rectangle2D button = ElementHelper.getSizeWithScale(
                properties.getBasicButtonSizeWidth(),
                properties.getBasicButtonSizeHeight());

        basicButton.setPrefWidth(button.getWidth());
        basicButton.setPrefHeight(button.getHeight());


//        ElementButtonKt.theme(basicButton, ElementButton.grayButton);

        BasicButton.basicButton = basicButton;
    }

    /**
     * @return
     */
    @Override
    public Button getContent() {
        return basicButton;
    }
}
