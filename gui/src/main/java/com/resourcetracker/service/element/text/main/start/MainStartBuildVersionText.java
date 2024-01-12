package com.resourcetracker.service.element.text.main.start;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.text.common.BuildVersionText;
import org.springframework.stereotype.Service;

/**
 * @see BuildVersionText
 */
@Service
public class MainStartBuildVersionText extends BuildVersionText {
  public MainStartBuildVersionText(PropertiesEntity properties) {
    super(properties);
  }
}
