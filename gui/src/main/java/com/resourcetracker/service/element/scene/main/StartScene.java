package com.resourcetracker.service.element.scene.main;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.layout.scene.main.StartSceneLayout;
import com.resourcetracker.service.element.menu.TabMenuBar;
import com.resourcetracker.service.element.progressbar.CircleProgressBar;
import com.resourcetracker.service.element.storage.ElementStorage;
import java.util.UUID;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** */
@Service
public class StartScene implements IElement<Scene> {
  UUID id = UUID.randomUUID();

  public StartScene(
      @Autowired PropertiesEntity properties,
      @Autowired StartSceneLayout startSceneLayout,
      @Autowired CircleProgressBar circleProgressBar,
      @Autowired TabMenuBar tabMenuBar) {
    Group group = new Group();
    group.getChildren().add(startSceneLayout.getContent());
    group.getChildren().add(circleProgressBar.getContent());
    group.getChildren().add(tabMenuBar.getContent());

    ElementStorage.setElement(
        id,
        new Scene(
            group,
            Color.rgb(
                properties.getStartSceneBackgroundColorR(),
                properties.getStartSceneBackgroundColorG(),
                properties.getStartSceneBackgroundColorB())));
  }

  public Scene getContent() {
    return ElementStorage.getElement(id);
  }
}
