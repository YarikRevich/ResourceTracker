package com.resourcetracker.service.element.layout.scene.main;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.button.BasicButton;
import com.resourcetracker.service.element.common.WindowHelper;
import com.resourcetracker.service.element.image.view.ConnectionStatusImageView;
import com.resourcetracker.service.element.layout.common.ButtonVBox;
import com.resourcetracker.service.element.layout.common.ConnectionStatusHBox;
import com.resourcetracker.service.element.menu.TabMenuBar;
import com.resourcetracker.service.element.scene.main.DeploymentDetailsScene;
import com.resourcetracker.service.element.scene.main.DeploymentScene;
import com.resourcetracker.service.element.scene.settings.SettingsLanguagesScene;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.state.LocalState;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StartSceneLayout implements IElementResizable, IElement<GridPane> {
    UUID id = UUID.randomUUID();

    public StartSceneLayout(
            @Autowired PropertiesEntity properties,
            @Autowired SettingsLanguagesScene settingsLanguagesScene,
            @Autowired  ConnectionStatusImageView connectionStatusImageView
    ) {
//        @Autowired DeploymentScene deploymentScene,
//        @Autowired DeploymentDetailsScene deploymentDetailsScene,
//        @Autowired SettingsLanguagesScene settingsLanguagesScene,


        GridPane grid = new GridPane();
        grid.setVgap(20);

        grid.setMinWidth(400);
        grid.setGridLinesVisible(true);

        ConnectionStatusHBox connectionStatusHBox = new ConnectionStatusHBox(
                connectionStatusImageView.getContent());

        ButtonVBox buttonVBox =
                new ButtonVBox(
                        new BasicButton(
                                "Start",
                                properties,
                                () -> {
                                    WindowHelper.switchScene(getContent().getScene(), settingsLanguagesScene.getContent());
                                    System.out.println("it start");
                                })
                                .getContent(),
                        new BasicButton(
                                "Deployment",
                                properties,
                                () -> {
                                    System.out.println("it deployment");
                                })
                                .getContent(),
                        new BasicButton(
                                "Settings",
                                properties,
                                () -> {
                                    System.out.println("it settings");
                                })
                                .getContent());

        grid.addRow(0, connectionStatusHBox.getContent());
        grid.addRow(1, buttonVBox.getContent());
//
//        // GraphVisualizer graphVisualizer = new GraphVisualizer();
//
//        CircleProgressBar circleProgressBar = new CircleProgressBar(properties);
//
//        Group group = new Group();
//        group.getChildren().add(buttonVBox.getContent());
//        group.getChildren().add(circleProgressBar.getContent());
//        group.getChildren().add(tabMenuBar.getContent());
//

        ElementStorage.setElement(id, grid);
        ElementStorage.setResizable(this);
    }

    /**
     * @return
     */
    @Override
    public GridPane getContent() {
        return ElementStorage.getElement(id);
    }

    /**
     *
     */
    @Override
    public void handlePrefWidth() {
        System.out.println(LocalState.getWindowWidth());

    }

    /**
     *
     */
    @Override
    public void handlePrefHeight() {
        System.out.println(LocalState.getWindowHeight());
    }
}
