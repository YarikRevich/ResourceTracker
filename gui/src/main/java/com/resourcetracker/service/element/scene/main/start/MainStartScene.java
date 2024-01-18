package com.resourcetracker.service.element.scene.main.start;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.layout.scene.main.start.MainStartSceneLayout;
import com.resourcetracker.service.element.menu.TabMenuBar;
import com.resourcetracker.service.element.progressbar.main.start.MainStartCircleProgressBar;
import com.resourcetracker.service.element.storage.ElementStorage;
import java.util.UUID;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** */
@Service
public class MainStartScene implements IElement<Scene> {
  UUID id = UUID.randomUUID();

  public MainStartScene(
      @Autowired PropertiesEntity properties,
      @Autowired MainStartSceneLayout startSceneLayout,
      @Autowired MainStartCircleProgressBar mainStartCircleProgressBar,
      @Autowired TabMenuBar tabMenuBar) {
    Group group = new Group();
    group.getChildren().add(tabMenuBar.getContent());
    group.getChildren().add(startSceneLayout.getContent());
    group.getChildren().add(mainStartCircleProgressBar.getContent());

    ElementStorage.setElement(
        id,
        new Scene(
            group,
            Color.rgb(
                properties.getGeneralBackgroundColorR(),
                properties.getGeneralBackgroundColorG(),
                properties.getGeneralBackgroundColorB())));
  }

  public Scene getContent() {
    return ElementStorage.getElement(id);
  }
}
