package com.resourcetracker.service.element.progressbar.main.start;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.progressbar.common.CircleProgressBar;
import org.springframework.stereotype.Service;

/**
 * @see CircleProgressBar
 */
@Service
public class MainStartCircleProgressBar extends CircleProgressBar {
  public MainStartCircleProgressBar(PropertiesEntity properties) {
    super(properties);
  }
}
