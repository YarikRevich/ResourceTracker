package com.resourcetracker.service.element.image.view.main.deployment;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.image.view.common.ConnectionStatusImageView;
import org.springframework.stereotype.Service;

/**
 * @see ConnectionStatusImageView
 */
@Service
public class MainDeploymentConnectionStatusImageView extends ConnectionStatusImageView {
  public MainDeploymentConnectionStatusImageView(PropertiesEntity properties) {
    super(properties);
  }
}
