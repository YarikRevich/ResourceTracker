package com.resourcetracker.service.element.progressbar.main.deployment;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.progressbar.common.CircleProgressBar;
import org.springframework.stereotype.Service;

/**
 * @see CircleProgressBar
 */
@Service
public class MainDeploymentCircleProgressBar extends CircleProgressBar {
  public MainDeploymentCircleProgressBar(PropertiesEntity properties) {
    super(properties);
  }
}
