package com.resourcetracker.service.element.scene.main;

import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import javafx.scene.Scene;
import org.springframework.stereotype.Service;

@Service
public class DeploymentScene implements IElementResizable, IElement<Scene> {
  /**
   * @return
   */
  @Override
  public Scene getContent() {
    return null;
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
