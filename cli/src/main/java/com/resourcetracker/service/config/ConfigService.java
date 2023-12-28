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
import java.io.*;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service for processing configuration file
 *
 * @author YarikRevich
 */
@Service
public class ConfigService {
  private static final Logger logger = LogManager.getLogger(ConfigService.class);

  private final PropertiesEntity properties;

  private InputStream configFile;

  private ConfigEntity parsedConfigFile;

  /**
   * Default constructor, which opens configuration file at the given path.
   *
   * @param properties common application properties
   */
  public ConfigService(@Autowired PropertiesEntity properties) {
    try {
      configFile =
          new FileInputStream(
              Paths.get(System.getProperty("user.home"), properties.getConfigRootPath(), properties.getConfigUserFilePath())
                  .toString());
    } catch (FileNotFoundException e) {
      logger.fatal(e.getMessage());
    }

    this.properties = properties;
  }

  /**
   * Reads configuration from the opened configuration file using mapping with a configuration
   * entity.
   */
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
   * Returns command, which needs to be executed by the user to
   * modify local ResourceTracker API Server configuration.
   * @param machineAddress Kafka host remote machine address.
   * @return Kafka host modification command.
   */
  public String getKafkaHostModificationCommand(String machineAddress) {
    return String.format("sed -i 's/host:.*/host: %s/g' '%s'",
            machineAddress,
            Paths.get(System.getProperty("user.home"), properties.getConfigRootPath(), properties.getConfigApiServerFilePath()));

    //sed -i "s/staging/${{ github.event.inputs.chainimage }}/g" "values/alpha_activeset/common/p0docker-compose-sharder.yml"
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
