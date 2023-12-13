package com.resourcetracker.service.element.layout.common;

import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.storage.ElementStorage;
import javafx.geometry.Pos;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;

import java.util.UUID;

/**
 *
 */
public class ConnectionStatusBox implements IElement<HBox> {
    UUID id = UUID.randomUUID();

    public ConnectionStatusBox(SplitPane connectionStatusImage) {
        HBox hBox = new HBox(connectionStatusImage);
        hBox.setAlignment(Pos.TOP_RIGHT);

        ElementStorage.setElement(id, hBox);
    }

    /**
     * @return
     */
    @Override
    public HBox getContent() {
        return ElementStorage.getElement(id);
    }
}
