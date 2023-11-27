package com.resourcetracker.service.stage;

import javafx.stage.Stage;

/**
 * SettingsStage represents settings window.
 */
public class SettingsStage {
    private final Stage settingsStage;

    public SettingsStage() {
        Stage settingsStage = new Stage();

        this.settingsStage = settingsStage;
    }

    public Stage getContent() {
        return settingsStage;
    }
}
