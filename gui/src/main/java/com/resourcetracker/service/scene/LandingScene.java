package com.resourcetracker.service.scene;

import com.resourcetracker.service.component.TabMenuBar;
import javafx.scene.Group;
import javafx.scene.Scene;

public class LandingScene extends Scene {
    public LandingScene() {
        super(null);

        TabMenuBar tabMenuBar = new TabMenuBar();

        Group group = new Group();
        group.getChildren().add(tabMenuBar.getContent());

        this.setRoot(group);
    }
}
