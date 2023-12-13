package com.resourcetracker.service.element.text;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.storage.ElementStorage;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BuildVersionText implements IElementResizable, IElement<Label> {
    UUID id = UUID.randomUUID();

    public BuildVersionText(@Autowired PropertiesEntity properties) {
        Label label = new Label(String.format("BuildVersion: %s", properties.getGitCommitId()));
        label.setAlignment(Pos.CENTER_LEFT);
        label.setMaxWidth(180);
        label.setWrapText(true);

        ElementStorage.setElement(id, label);
    }

    /**
     * @see IElement
     */
    @Override
    public Label getContent() {
        return ElementStorage.getElement(id);
    }

    /**
     * @see IElementResizable
     */
    @Override
    public void handlePrefWidth() {

    }

    /**
     * @see IElementResizable
     */
    @Override
    public void handlePrefHeight() {

    }
}
