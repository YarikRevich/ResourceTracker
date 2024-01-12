package com.resourcetracker.service.element.image.view.main.start;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.image.view.common.ConnectionStatusImageView;
import org.springframework.stereotype.Service;

/**
 * @see ConnectionStatusImageView
 */
@Service
public class MainStartConnectionStatusImageView extends ConnectionStatusImageView {
  public MainStartConnectionStatusImageView(PropertiesEntity properties) {
    super(properties);
  }
}
