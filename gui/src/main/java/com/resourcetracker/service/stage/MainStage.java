package com.resourcetracker.service.stage;

import com.resourcetracker.service.scene.LandingScene;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * MainStage represents main window.
 */
public class MainStage {
    private final Stage mainStage;

    public MainStage() {
        Stage mainStage = new Stage();

        mainStage.setTitle("ResourceTracker GUI");

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        mainStage.setX((primScreenBounds.getWidth() - mainStage.getWidth()) / 2);
        mainStage.setY((primScreenBounds.getHeight() - mainStage.getHeight()) / 2);

        LandingScene landingScene = new LandingScene();
        mainStage.setScene(landingScene.getContent());

        this.mainStage = mainStage;
    }

    public Stage getContent() {
        return mainStage;
    }
}
