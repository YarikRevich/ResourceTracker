package com.resourcetracker.service.element.scene;

import com.resourcetracker.service.element.menu.TabMenuBar;
import com.resourcetracker.service.element.IElement;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;

public class SettingsLanguagesScene implements IElement<Scene> {
    private final Scene settingsLanguagesScene;

    public SettingsLanguagesScene() {
        TabMenuBar tabMenuBar = new TabMenuBar();

        var label = new Label("Hello world!");

        Group group = new Group();
        group.getChildren().add(tabMenuBar.getContent());
        group.getChildren().add(label);

        group.minHeight(200);
        group.minWidth(300);

        this.settingsLanguagesScene = new Scene(group);
    }

    public Scene getContent() {
        return settingsLanguagesScene;
    }
}
