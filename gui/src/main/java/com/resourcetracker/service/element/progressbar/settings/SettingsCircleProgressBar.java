package com.resourcetracker.service.element.progressbar.settings;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.progressbar.common.CircleProgressBar;
import org.springframework.stereotype.Service;

/**
 * @see CircleProgressBar
 */
@Service
public class SettingsCircleProgressBar extends CircleProgressBar {
  public SettingsCircleProgressBar(PropertiesEntity properties) {
    super(properties);
  }
}
