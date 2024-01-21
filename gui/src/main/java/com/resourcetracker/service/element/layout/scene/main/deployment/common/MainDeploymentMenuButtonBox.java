package com.resourcetracker.service.element.layout.scene.main.deployment.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.layout.scene.main.common.MainMenuButtonBox;
import com.resourcetracker.service.element.progressbar.main.deployment.MainDeploymentCircleProgressBar;
import com.resourcetracker.service.element.progressbar.main.start.MainStartCircleProgressBar;
import com.resourcetracker.service.element.stage.SettingsStage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/** Represents deployment menu button box used for main stage. */
@Service
public class MainDeploymentMenuButtonBox extends MainMenuButtonBox {
  public MainDeploymentMenuButtonBox(
      PropertiesEntity properties,
      ApplicationEventPublisher applicationEventPublisher,
      SettingsStage settingsStage,
      MainStartCircleProgressBar mainStartCircleProgressBar,
      MainDeploymentCircleProgressBar mainDeploymentCircleProgressBar) {
    super(
        properties,
        applicationEventPublisher,
        settingsStage,
        mainStartCircleProgressBar,
        mainDeploymentCircleProgressBar);
  }
}
