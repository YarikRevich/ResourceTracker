package com.resourcetracker.service.element.stage;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.common.ElementHelper;
import com.resourcetracker.service.element.scene.StartScene;
import com.resourcetracker.service.event.state.ConnectionStatusEvent;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * MainStage represents main window.
 */
@Service
public class MainStage implements IElement<Stage> {
    private static Stage mainStage;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public MainStage(
            @Autowired PropertiesEntity properties,
            @Autowired StartScene startScene
            ) {
        Platform.runLater(() -> {
            Stage mainStage = new Stage();
            mainStage.setTitle(properties.getWindowMainName());

            Rectangle2D window = ElementHelper.getSizeWithScale(
                    properties.getWindowMainScaleWidth(),
                    properties.getWindowMainScaleHeight());
            mainStage.setWidth(window.getWidth());
            mainStage.setHeight(window.getHeight());

            Point2D centralPoint = ElementHelper.getCentralPoint(mainStage.getWidth(), mainStage.getHeight());
            mainStage.setX(centralPoint.getX());
            mainStage.setY(centralPoint.getY());

            mainStage.setScene(startScene.getContent());

            MainStage.mainStage = mainStage;
        });
    }

    public Stage getContent() {
        applicationEventPublisher.publishEvent(new ConnectionStatusEvent(true));
        return mainStage;
    }
}
