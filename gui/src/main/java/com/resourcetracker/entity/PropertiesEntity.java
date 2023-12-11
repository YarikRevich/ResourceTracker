package com.resourcetracker.entity;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/** Represents application properties used for application configuration. */
@Getter
@Configuration
public class PropertiesEntity {
  @Value(value = "${window.main.name}")
  String windowMainName;

  @Value(value = "${window.main.scale.width}")
  Double windowMainScaleWidth;

  @Value(value = "${window.main.scale.height}")
  Double windowMainScaleHeight;

  @Value(value = "${window.settings.name}")
  String windowSettingsName;

  @Value(value = "${window.settings.scale.width}")
  Double windowSettingsScaleWidth;

  @Value(value = "${window.settings.scale.height}")
  Double windowSettingsScaleHeight;

  @Value(value = "${process.background.period}")
  Integer processBackgroundPeriod;

  @Value(value = "${process.window.width.period}")
  Integer processWindowWidthPeriod;

  @Value(value = "${process.window.height.period}")
  Integer processWindowHeightPeriod;

  @Value(value = "${button.basic.size.width}")
  Double basicButtonSizeWidth;

  @Value(value = "${button.basic.size.height}")
  Double basicButtonSizeHeight;

  @Value(value = "${scene.start.background.color.r}")
  Integer startSceneBackgroundColorR;

  @Value(value = "${scene.start.background.color.g}")
  Integer startSceneBackgroundColorG;

  @Value(value = "${scene.start.background.color.b}")
  Integer startSceneBackgroundColorB;

  @Value(value = "${image.status.scale}")
  Double statusImageScale;

  @Value(value = "${api-server.directory}")
  String apiServerDirectory;
}
