package com.resourcetracker.service.element.layout.scene.main.start.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.image.view.main.start.MainStartConnectionStatusImageView;
import com.resourcetracker.service.element.layout.scene.main.common.MainHeaderGrid;
import org.springframework.stereotype.Service;

/**
 * @see MainHeaderGrid
 */
@Service
public class MainStartHeaderGrid extends MainHeaderGrid {
  public MainStartHeaderGrid(
      PropertiesEntity properties,
      MainStartConnectionStatusImageView mainStartConnectionStatusImageView) {
    super(properties, mainStartConnectionStatusImageView);
  }
}
