package com.resourcetracker.service.element.scene;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.button.BasicButton;
import com.resourcetracker.service.element.common.ElementHelper;
import com.resourcetracker.service.element.graph.GraphVisualizer;
import com.resourcetracker.service.element.layout.buttonvbox.ButtonVBox;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StartScene implements IElement<Scene> {
    UUID id = UUID.randomUUID();

    public StartScene(
            @Autowired PropertiesEntity properties,
            @Autowired SettingsLanguagesScene settingsLanguagesScene
            ) {
        ButtonVBox buttonVBox = new ButtonVBox(
                properties,
                new BasicButton(
                        "Start", properties, () -> {
                            ElementHelper.switchScene(getContent(), settingsLanguagesScene.getContent());
                            System.out.println("it start");
                }).getContent(),
                new BasicButton(
                        "Deployment", properties, () -> {
                            System.out.println("it deployment");
                }).getContent(),
                new BasicButton(
                        "Settings", properties, () -> {

                            System.out.println("it settings");
                }).getContent());

        GraphVisualizer graphVisualizer = new GraphVisualizer();

//        TabMenuBar tabMenuBar = new TabMenuBar();
//        var label = new Label("Hello world!");
//        var scene = new Scene(new StackPane(label), 640, 480);
//        mainStage.setScene(scene);

        Group group = new Group();
        group.getChildren().add(buttonVBox.getContent());
        group.getChildren().add(graphVisualizer.getContent());
//        group.getChildren().add(tabMenuBar.getContent());
//        group.getChildren().add(ConnectionStatusImageCollection.getSuccessfulConnectionStatusImage());

        setElement(
                id,
                new Scene(
                        group,
                        Color.rgb(
                            properties.getStartSceneBackgroundColorR(),
                            properties.getStartSceneBackgroundColorG(),
                            properties.getStartSceneBackgroundColorB()
                        )
                )
        );
    }

    public Scene getContent() {
        return getElement(id);
    }
}
