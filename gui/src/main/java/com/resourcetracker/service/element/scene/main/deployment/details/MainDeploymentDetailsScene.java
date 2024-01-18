package com.resourcetracker.service.element.scene.main.deployment.details;

import com.resourcetracker.service.element.IElement;
import com.resourcetracker.service.element.IElementResizable;
import javafx.scene.Scene;
import org.springframework.stereotype.Service;

@Service
public class MainDeploymentDetailsScene implements IElementResizable, IElement<Scene> {
  /**
   * @return
   */
  @Override
  public Scene getContent() {
    return null;
  }

  /** */
  @Override
  public void handlePrefWidth() {}

  /** */
  @Override
  public void handlePrefHeight() {}
}
