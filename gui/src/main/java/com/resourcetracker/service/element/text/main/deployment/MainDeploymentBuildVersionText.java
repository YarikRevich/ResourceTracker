package com.resourcetracker.service.element.text.main.deployment;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.text.common.BuildVersionText;
import org.springframework.stereotype.Service;

/**
 * @see BuildVersionText
 */
@Service
public class MainDeploymentBuildVersionText extends BuildVersionText {
  public MainDeploymentBuildVersionText(PropertiesEntity properties) {
    super(properties);
  }
}
