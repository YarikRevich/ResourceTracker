package com.resourcetracker.service.element.progressbar;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import com.resourcetracker.service.element.common.WindowHelper;
import java.util.UUID;

import com.resourcetracker.service.element.storage.ElementStorage;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;

/** */
public class CircleProgressBar implements IElementResizable, IElement<VBox> {
  UUID id = UUID.randomUUID();

  public CircleProgressBar(PropertiesEntity properties) {
    ProgressIndicator progressBar = new ProgressIndicator();
    progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

    VBox vbox = new VBox(progressBar);
    vbox.setAlignment(Pos.CENTER);

    Rectangle2D window =
        WindowHelper.getSizeWithScale(
            properties.getWindowMainScaleWidth(), properties.getWindowMainScaleHeight());
    Point2D centralPoint = WindowHelper.getCentralPoint(window.getWidth(), window.getHeight());

    vbox.setTranslateX(centralPoint.getX() - 20);
    vbox.setTranslateY(centralPoint.getY() + 20);
    //        Rectangle2D window =
    //                ElementHelper.getSizeWithScale(
    //                        properties.getWindowMainScaleWidth(),
    // properties.getWindowMainScaleHeight());
    //        vbox.setMaxWidth(window.getWidth());
    //        vbox.setMaxHeight(window.getHeight());

    ElementStorage.setElement(id, vbox);
    ElementStorage.setResizable(this);

    //
    //        ProgressBar bar = new ProgressBar(0);
    //        bar.setPrefSize(200, 24);
    //        Timeline task = new Timeline(
    //                new KeyFrame(
    //                        Duration.ZERO,
    //                        new KeyValue(bar.progressProperty(), 0)
    //                ),
    //                new KeyFrame(
    //                        Duration.seconds(2),
    //                        new KeyValue(bar.progressProperty(), 1)
    //                )
    //        );
    //        Button button = new Button("Go!");
    //        button.setOnAction(new EventHandler() {
    //            @Override public void handle(ActionEvent actionEvent) {
    //                task.playFromStart();
    //            }
    //        });
    //        VBox layout = new VBox(10);
    //        layout.getChildren().setAll(
    //                bar,
    //                button
    //        );
    //        layout.setPadding(new Insets(10));
    //        layout.setAlignment(Pos.CENTER);
    //        layout.getStylesheets().add(
    //                getClass().getResource(
    //                        "striped-progress.css"
    //                ).toExternalForm()
    //        );
    //        stage.setScene(new Scene(layout));
    //        stage.show();
  }

  // create a progressbar
  //    ProgressBar pb = new ProgressBar();
  //
  //    // create a tile pane
  //    TilePane r = new TilePane();
  //
  //    // action event
  //    EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
  //        public void handle(ActionEvent e)
  //        {
  //            // set progress to different level of progressbar
  //            ii += 0.1;
  //            pb.setProgress(ii);
  //        }
  //
  //    };
  //
  //    // creating button
  //    Button b = new Button("increase");
  //
  //    // set on action
  //        b.setOnAction(event);

  /**
   * @return
   */
  @Override
  public VBox getContent() {
    return ElementStorage.getElement(id);
  }

  /**
   *
   */
  @Override
  public void handlePrefWidth() {

  }

  /**
   *
   */
  @Override
  public void handlePrefHeight() {

  }
}
