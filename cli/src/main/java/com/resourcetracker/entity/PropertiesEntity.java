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

  @Value(value = "${git.commit.id.abbrev}")
  private String gitCommitId;

  @Value("${config.root}")
  private String configRootPath;

  @Value("${config.user.file}")
  private String configUserFilePath;

  @Value("${progress.visualization.period}")
  private Integer progressVisualizationPeriod;

  @Value("${progress.visualization.secrets-acquire-request}")
  private String progressVisualizationSecretsAcquireRequestLabel;

  @Value("${progress.visualization.script-acquire-request}")
  private String progressVisualizationScriptAcquireRequestLabel;

  @Value("${progress.visualization.version-info-request}")
  private String progressVisualizationVersionInfoRequestLabel;

  @Bean
  private static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
    PropertySourcesPlaceholderConfigurer propsConfig = new PropertySourcesPlaceholderConfigurer();
    propsConfig.setLocation(new ClassPathResource(GIT_CONFIG_PROPERTIES_FILE));
    propsConfig.setIgnoreResourceNotFound(true);
    propsConfig.setIgnoreUnresolvablePlaceholders(true);
    return propsConfig;
  }
}
