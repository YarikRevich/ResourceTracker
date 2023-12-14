package com.resourcetracker.service.element.text;

import static java.lang.Integer.MAX_VALUE;

import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.storage.ElementStorage;
import java.util.UUID;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.springframework.stereotype.Service;

@Service
public class LandingAnnouncementText implements IElement<Label> {
  UUID id = UUID.randomUUID();

  public LandingAnnouncementText() {
    Label label = new Label("Слава. Your long text here\nIt works");
    label.setFont(new Font(20));
    label.setAlignment(Pos.TOP_CENTER);
    label.setMaxWidth(MAX_VALUE);
    label.setMaxHeight(MAX_VALUE);
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
