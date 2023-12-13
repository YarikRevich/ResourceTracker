package com.resourcetracker.service.element.layout.common;

import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.image.view.ConnectionStatusImageView;
import com.resourcetracker.service.element.storage.ElementStorage;
import javafx.geometry.Pos;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

import java.util.UUID;

/**
 *
 */
public class ConnectionStatusHBox implements IElement<HBox> {
    UUID id = UUID.randomUUID();

    public ConnectionStatusHBox(SplitPane connectionStatusImage) {
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
