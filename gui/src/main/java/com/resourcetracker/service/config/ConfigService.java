package com.resourcetracker.service.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.entity.PropertiesEntity;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Service for processing configuration file
 *
 * @author YarikRevich
 */
@Service
public class ConfigService {
  private static final Logger logger = LogManager.getLogger(ConfigService.class);

  private InputStream configFile;

  private ConfigEntity parsedConfigFile;

  /** Opens YAML configuration file */
  public ConfigService(PropertiesEntity properties) {
    try {
      configFile =
          new FileInputStream(
              Paths.get(
                      System.getProperty("user.home"),
                      properties.getConfigRootPath(),
                      properties.getConfigUserFilePath())
                  .toString());
    } catch (FileNotFoundException e) {
      logger.fatal(e.getMessage());
    }
  }

  /** Processes configuration file */
  @PostConstruct
  private void process() {
    ObjectMapper mapper =
        new ObjectMapper(new YAMLFactory())
            .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    ObjectReader reader = mapper.reader().forType(new TypeReference<ConfigEntity>() {});

    try {
      parsedConfigFile = reader.<ConfigEntity>readValues(configFile).readAll().getFirst();
    } catch (IOException e) {
      logger.fatal(e.getMessage());
    }
  }

  /**
   * @return Parsed configuration entity
   */
  public ConfigEntity getConfig() {
    return parsedConfigFile;
  }

  @PreDestroy
  private void close() {
    try {
      configFile.close();
    } catch (IOException e) {
      logger.fatal(e.getMessage());
    }
  }
}