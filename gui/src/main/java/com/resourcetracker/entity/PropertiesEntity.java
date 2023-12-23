package com.resourcetracker.entity;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/** Represents application properties used for application configuration. */
@Getter
@Configuration
public class PropertiesEntity {
  private static final String GIT_CONFIG_PROPERTIES_FILE = "git.properties";

  @Value(value = "${window.main.name}")
  String windowMainName;

  @Value(value = "${window.main.scale.min.width}")
  Double windowMainScaleMinWidth;

  @Value(value = "${window.main.scale.min.height}")
  Double windowMainScaleMinHeight;

  @Value(value = "${window.main.scale.max.width}")
  Double windowMainScaleMaxWidth;

  @Value(value = "${window.main.scale.max.height}")
  Double windowMainScaleMaxHeight;

  @Value(value = "${window.settings.name}")
  String windowSettingsName;

  @Value(value = "${window.settings.scale.width}")
  Double windowSettingsScaleWidth;

  @Value(value = "${window.settings.scale.height}")
  Double windowSettingsScaleHeight;

  @Value(value = "${process.background.period}")
  Integer processBackgroundPeriod;

  @Value(value = "${process.healthcheck.period}")
  Integer processHealthcheckPeriod;

  @Value(value = "${process.window.width.period}")
  Integer processWindowWidthPeriod;

  @Value(value = "${process.window.height.period}")
  Integer processWindowHeightPeriod;

  @Value(value = "${spinner.initial.delay}")
  Integer spinnerInitialDelay;

  @Value(value = "${button.basic.size.width}")
  Double basicButtonSizeWidth;

  @Value(value = "${button.basic.size.height}")
  Double basicButtonSizeHeight;

  @Value(value = "${scene.general.background.color.r}")
  Integer generalBackgroundColorR;

  @Value(value = "${scene.general.background.color.g}")
  Integer generalBackgroundColorG;

  @Value(value = "${scene.general.background.color.b}")
  Integer generalBackgroundColorB;

  @Value(value = "${scene.common.header.background.color.r}")
  Integer commonSceneHeaderBackgroundColorR;

  @Value(value = "${scene.common.header.background.color.g}")
  Integer commonSceneHeaderBackgroundColorG;

  @Value(value = "${scene.common.header.background.color.b}")
  Integer commonSceneHeaderBackgroundColorB;

  @Value(value = "${scene.common.header.connection.background.color.r}")
  Integer commonSceneHeaderConnectionStatusBackgroundColorR;

  @Value(value = "${scene.common.header.connection.background.color.g}")
  Integer commonSceneHeaderConnectionStatusBackgroundColorG;

  @Value(value = "${scene.common.header.connection.background.color.b}")
  Integer commonSceneHeaderConnectionStatusBackgroundColorB;

  @Value(value = "${scene.common.menu.background.color.r}")
  Integer commonSceneMenuBackgroundColorR;

  @Value(value = "${scene.common.menu.background.color.g}")
  Integer commonSceneMenuBackgroundColorG;

  @Value(value = "${scene.common.menu.background.color.b}")
  Integer commonSceneMenuBackgroundColorB;

  @Value(value = "${scene.common.content.background.color.r}")
  Integer commonSceneContentBackgroundColorR;

  @Value(value = "${scene.common.content.background.color.g}")
  Integer commonSceneContentBackgroundColorG;

  @Value(value = "${scene.common.content.background.color.b}")
  Integer commonSceneContentBackgroundColorB;

  @Value(value = "${scene.common.footer.background.color.r}")
  Integer commonSceneFooterBackgroundColorR;

  @Value(value = "${scene.common.footer.background.color.g}")
  Integer commonSceneFooterBackgroundColorG;

  @Value(value = "${scene.common.footer.background.color.b}")
  Integer commonSceneFooterBackgroundColorB;

  @Value(value = "${image.status.scale}")
  Double statusImageScale;

  @Value(value = "${graph.css.location}")
  String graphCssFileLocation;

  @Value(value = "${graph.properties.location}")
  String graphPropertiesLocation;

  @Value(value = "${api-server.directory}")
  String apiServerDirectory;

  @Value(value = "${git.commit.id.abbrev}")
  String gitCommitId;

  @Bean
  public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
    PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();
    propsConfig.setLocation(new ClassPathResource(GIT_CONFIG_PROPERTIES_FILE));
    propsConfig.setIgnoreResourceNotFound(true);
    propsConfig.setIgnoreUnresolvablePlaceholders(true);
    return propsConfig;
  }
}
