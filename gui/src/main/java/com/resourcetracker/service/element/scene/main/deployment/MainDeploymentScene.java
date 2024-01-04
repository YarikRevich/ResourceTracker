package com.resourcetracker.service.element.scene.main.deployment;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.layout.scene.main.deployment.MainDeploymentSceneLayout;
import com.resourcetracker.service.element.progressbar.stage.main.MainCircleProgressBar;
import com.resourcetracker.service.element.storage.ElementStorage;
import java.util.UUID;
import javafx.scene.Group;
import javafx.scene.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainDeploymentScene implements IElement<Scene> {
  UUID id = UUID.randomUUID();

  public MainDeploymentScene(
      @Autowired PropertiesEntity properties,
      @Autowired MainDeploymentSceneLayout deploymentSceneLayout,
      @Autowired MainCircleProgressBar circleProgressBar) {
    Group group = new Group();
    group.getChildren().add(deploymentSceneLayout.getContent());
    group.getChildren().add(circleProgressBar.getContent());

    //    ElementStorage.setElement(
    //            id,
    //            new Scene(
    //                    group,
    //                    Color.rgb(
    //                            properties.getGeneralBackgroundColorR(),
    //                            properties.getGeneralBackgroundColorG(),
    //                            properties.getGeneralBackgroundColorB())));
  }

  @Override
  public Scene getContent() {
    return ElementStorage.getElement(id);
  }
}
