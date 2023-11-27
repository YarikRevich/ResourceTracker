package com.resourcetracker.service.scene;

import com.resourcetracker.service.component.TabMenuBar;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class LandingScene {
    private final Scene landingScene;

    public LandingScene() {
        TabMenuBar tabMenuBar = new TabMenuBar();
//        var label = new Label("Hello world!");
//        var scene = new Scene(new StackPane(label), 640, 480);
//        mainStage.setScene(scene);

        Group group = new Group();
        group.getChildren().add(tabMenuBar.getContent());
//        group.getChildren().add(label);

//        group.minHeight(200);
//        group.minWidth(300);

        this.landingScene = new Scene(group);
    }

    public Scene getContent() {
        return landingScene;
    }
}
