package com.resourcetracker.service.element.layout.common;

import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.storage.ElementStorage;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

import java.util.UUID;

public class LandingHBox implements IElementResizable, IElement<HBox> {
    UUID id = UUID.randomUUID();

    public LandingHBox(Node... elements) {
        HBox hbox = new HBox(20, elements);

        ElementStorage.setElement(id, hbox);
    }

    /**
     * @return
     */
    @Override
    public HBox getContent() {
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
