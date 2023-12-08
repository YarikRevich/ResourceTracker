package com.resourcetracker.service.element.layout.buttonvbox;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.button.BasicButton;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.UUID;

public class ButtonVBox implements IElement<VBox> {
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
}
