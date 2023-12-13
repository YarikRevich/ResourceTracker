package com.resourcetracker.service.element.common;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Represents helpful functionality, which is used for elements management. */
public class WindowHelper {
  /**
   * Executes scene switch
   *
   * @param prev
   * @param next
   */
  public static void switchScene(Scene prev, Scene next) {
    ((Stage) prev.getWindow()).setScene(next);
  }

  /**
   * Switches visibility of the given element.
   *
   * @param element element which visibility is intended to be changed.
   */
  public static void toggleElementVisibility(Node element) {
    element.setVisible(!element.isVisible());
  }

  /** Retrieves */
  public static Point2D getCentralPoint(Double width, Double height) {
    return new Point2D(width / 2, height / 2);
  }

  public static Rectangle2D getSizeWithScale(
      Double windowWidth, Double windowHeight, Double widthScale, Double heightScale) {
    return new Rectangle2D(0, 0, windowWidth * widthScale, windowHeight * heightScale);
  }

  public static Double getCircularElementSize(Double windowWidth, Double scale) {
    return windowWidth * scale;
  }
}
