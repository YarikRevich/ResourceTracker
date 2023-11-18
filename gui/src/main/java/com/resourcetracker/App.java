package com.resourcetracker;

import com.resourcetracker.service.stage.MainStage;
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


    @Override
    public void init() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    /**
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        MainStage mainStage = new MainStage();

        var label = new Label("Hello world!");
        var scene = new Scene(new StackPane(label), 640, 480);
        mainStage.setScene(scene);
        mainStage.show();
    }
}
