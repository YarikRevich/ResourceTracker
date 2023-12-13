package com.resourcetracker.service.element.text;

import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.state.LocalState;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LandingAnnouncementText implements IElement<Label> {
    UUID id = UUID.randomUUID();

    public LandingAnnouncementText() {
        Label label = new Label("Your long text here");
        label.setAlignment(Pos.CENTER);
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
}
