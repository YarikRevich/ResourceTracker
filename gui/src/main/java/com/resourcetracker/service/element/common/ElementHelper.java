package com.resourcetracker.service.element.common;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/** Represents helpful functionality, which is used for elements management. */
public class ElementHelper {
  /**
   * Executes scene switch
   *
   * @param prev
   * @param next
   */
  public static void switchScene(Scene prev, Scene next) {
    ((Stage) prev.getWindow()).setScene(next);
  }

  //
  //    public static void switchStage(Stage prev, Stage next) {
  //        prev.close();
  //        next.show();
  //    }

  /** Retrieves */
  public static Point2D getCentralPoint(Double width, Double height) {
    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();

    return new Point2D(
        (primScreenBounds.getWidth() - width) / 2, (primScreenBounds.getHeight() - height) / 2);
  }

  public static Rectangle2D getSizeWithScale(Double widthScale, Double heightScale) {
    Rectangle2D defaultBounds = Screen.getPrimary().getVisualBounds();

    return new Rectangle2D(
        0, 0, defaultBounds.getWidth() * widthScale, defaultBounds.getHeight() * heightScale);
  }
}
