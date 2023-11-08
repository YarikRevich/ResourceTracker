package com.resourcetracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    /**
     *
     */
    public void launch() {
        Application.launch();
    }

    /**
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        var label = new Label("Hello world!");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}
