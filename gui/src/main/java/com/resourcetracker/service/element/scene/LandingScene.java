package com.resourcetracker.service.element.scene;

import com.resourcetracker.service.component.TabMenuBar;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.button.BasicButton;
import com.resourcetracker.service.element.image.collection.ConnectionStatusImageCollection;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LandingScene implements IElement<Scene> {
    private final Scene landingScene;

    public LandingScene(
            @Autowired BasicButton landingButton,
            @Autowired BasicButton deploymentButton,
            @Autowired BasicButton settingsButton
    ) {
//        TabMenuBar tabMenuBar = new TabMenuBar();
//        var label = new Label("Hello world!");
//        var scene = new Scene(new StackPane(label), 640, 480);
//        mainStage.setScene(scene);

        landingButton.getContent().setText("Start");
        deploymentButton.getContent().setText("Deployment");
        settingsButton.getContent().setText("Settings");


        Group group = new Group();
        group.getChildren().add(landingButton.getContent());
//        group.getChildren().add(deploymentButton.getContent());
//        group.getChildren().add(settingsButton.getContent());
//        group.getChildren().add(tabMenuBar.getContent());
//        group.getChildren().add(ConnectionStatusImageCollection.getSuccessfulConnectionStatusImage());
//
//        basicButton.getContent().minHeight(200);
//        basicButton.getContent().minWidth(300);

        this.landingScene = new Scene(group);
    }

    public Scene getContent() {
        return landingScene;
    }
}
