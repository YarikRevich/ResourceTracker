package com.resourcetracker.service.stage;

import com.resourcetracker.service.scene.LandingScene;
import javafx.stage.Stage;

/**
 * MainStage represents main window.
 */
public class MainStage extends Stage {
    public MainStage() {
        super.setTitle("ResourceTracker GUI");

        LandingScene landingScene = new LandingScene();

        super.setScene(landingScene);
    }
}
