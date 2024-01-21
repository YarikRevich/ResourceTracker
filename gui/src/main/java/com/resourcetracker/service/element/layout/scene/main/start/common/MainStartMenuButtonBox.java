package com.resourcetracker.service.element.layout.scene.main.start.common;

import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.service.element.layout.scene.main.common.MainMenuButtonBox;
import com.resourcetracker.service.element.progressbar.main.deployment.MainDeploymentCircleProgressBar;
import com.resourcetracker.service.element.progressbar.main.start.MainStartCircleProgressBar;
import com.resourcetracker.service.element.stage.SettingsStage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class MainStartMenuButtonBox extends MainMenuButtonBox {
  public MainStartMenuButtonBox(
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
