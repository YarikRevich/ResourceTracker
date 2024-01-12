package com.resourcetracker.service.element.layout.scene.main.start.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.layout.scene.main.common.MainFooterGrid;
import com.resourcetracker.service.element.text.main.start.MainStartBuildVersionText;
import org.springframework.stereotype.Service;

@Service
public class MainStartFooterGrid extends MainFooterGrid {
  public MainStartFooterGrid(
      PropertiesEntity properties, MainStartBuildVersionText mainStartBuildVersionText) {
    super(properties, mainStartBuildVersionText);
  }
}
