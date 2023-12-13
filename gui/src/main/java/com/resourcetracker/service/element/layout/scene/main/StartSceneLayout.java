package com.resourcetracker.service.element.layout.scene.main;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.button.BasicButton;
import com.resourcetracker.service.element.common.WindowHelper;
import com.resourcetracker.service.element.graph.GraphVisualizer;
import com.resourcetracker.service.element.image.view.ConnectionStatusImageView;
import com.resourcetracker.service.element.layout.common.ButtonVBox;
import com.resourcetracker.service.element.layout.common.ConnectionStatusHBox;
import com.resourcetracker.service.element.layout.common.LandingHBox;
import com.resourcetracker.service.element.menu.TabMenuBar;
import com.resourcetracker.service.element.scene.main.DeploymentDetailsScene;
import com.resourcetracker.service.element.scene.main.DeploymentScene;
import com.resourcetracker.service.element.scene.main.StartScene;
import com.resourcetracker.service.element.scene.settings.SettingsLanguagesScene;
import com.resourcetracker.service.element.stage.SettingsStage;
import com.resourcetracker.service.element.storage.ElementStorage;
import com.resourcetracker.service.event.state.LocalState;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.UUID;

@Service
public class StartSceneLayout implements IElementResizable, IElement<GridPane> {
    UUID id = UUID.randomUUID();

    @Lazy
    @Autowired
    private StartScene startScene;

    @Lazy
    @Autowired
    private DeploymentScene deploymentScene;

    @Lazy
    @Autowired
    private DeploymentDetailsScene deploymentDetailsScene;

    public StartSceneLayout(
            @Autowired PropertiesEntity properties,
            @Autowired SettingsStage settingsStage,
            @Autowired ConnectionStatusImageView connectionStatusImageView,
            @Autowired GraphVisualizer graphVisualizer
    ) {

        GridPane grid = new GridPane();
        grid.setVgap(20);

        grid.setGridLinesVisible(true);

        ConnectionStatusHBox connectionStatusHBox = new ConnectionStatusHBox(
                connectionStatusImageView.getContent());

        ButtonVBox buttonVBox =
                new ButtonVBox(
                        new BasicButton(
                                "Start",
                                properties,
                                () -> WindowHelper.switchScene(getContent().getScene(), startScene.getContent()))
                                .getContent(),
                        new BasicButton(
                                "Deployment",
                                properties,
                                () -> WindowHelper.switchScene(getContent().getScene(), deploymentScene.getContent()))
                                .getContent(),
                        new BasicButton(
                                "Settings",
                                properties,
                                () -> settingsStage.getContent().show())
                                .getContent());

//        graphVisualizer.getContent().setTranslateX(100);
//        graphVisualizer.getContent().setTranslateY(100);

        Label label = new Label("Your long text here");
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(180);
        label.setWrapText(true);

        LandingHBox landingHBox = new LandingHBox(
                buttonVBox.getContent(), label);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);

        grid.getColumnConstraints().add(0, column1);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(5);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(70);

        grid.getRowConstraints().add(0, row1);
        grid.getRowConstraints().add(1, row2);

        grid.addColumn(0, connectionStatusHBox.getContent(),
                landingHBox.getContent());

//        grid.addRow(0,
//                );
//        grid.addRow(1, );
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
        getContent().setMinWidth(LocalState.getWindowWidth());
    }

    /**
     *
     */
    @Override
    public void handlePrefHeight() {
        getContent().setMinHeight(LocalState.getWindowHeight());
    }
}
