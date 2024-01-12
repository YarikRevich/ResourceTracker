package com.resourcetracker.service.element.layout.scene.main.deployment.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.layout.scene.main.common.MainFooterGrid;
import com.resourcetracker.service.element.text.main.deployment.MainDeploymentBuildVersionText;
import org.springframework.stereotype.Service;

/**
 * @see MainFooterGrid
 */
@Service
public class MainDeploymentFooterGrid extends MainFooterGrid {
  public MainDeploymentFooterGrid(
      PropertiesEntity properties, MainDeploymentBuildVersionText mainDeploymentBuildVersionText) {
    super(properties, mainDeploymentBuildVersionText);
  }
}
