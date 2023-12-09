package com.resourcetracker.service.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.resourcetracker.entity.ConfigEntity;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.*;
import java.nio.file.Paths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

  private InputStream configFile;

  private ConfigEntity parsedConfigFile;

  /**
   * Default constructor, which opens configuration file at the given path.
   *
   * @param configRootPath base path to the configuration file
   * @param configFilePath name of the configuration file
   */
  public ConfigService(
      @Value("${config.root}") String configRootPath,
      @Value("${config.file}") String configFilePath) {
    try {
      configFile =
          new FileInputStream(
              Paths.get(System.getProperty("user.home"), configRootPath, configFilePath)
                  .toString());
    } catch (FileNotFoundException e) {
      logger.fatal(e.getMessage());
    }
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

  //    /**
  //     * Extracts data from the given script file.
  //     * @param src path to the script file
  //     * @return data from the given script file
  //     */
  //    private String getFileContent(String src) {
  //        BufferedReader reader = null;
  //        try {
  //            reader = new BufferedReader(new FileReader(src));
  //        } catch (FileNotFoundException e) {
  //            logger.fatal(e.getMessage());
  //        }
  //
  //        StringBuilder result = new StringBuilder();
  //
  //        String currentLine = null;
  //        try {
  //            currentLine = reader.readLine();
  //        } catch (IOException e) {
  //            logger.fatal(e.getMessage());
  //        }
  //
  //        while (currentLine != null) {
  //            result.append(currentLine);
  //
  //            try {
  //                currentLine = reader.readLine();
  //            } catch (IOException e) {
  //                logger.fatal(e.getMessage());
  //            }
  //        }
  //
  //        try {
  //            reader.close();
  //        } catch (IOException e) {
  //            logger.fatal(e.getMessage());
  //        }
  //
  //        return result.toString();
  //    }
  //
  //    /**
  //     * Selects explicit script, if given, or retrieves
  //     * it from the given script file. If both are not given
  //     * throws exception.
  //     * @param src request entity, where both explicit script
  //     *            and script file can be found
  //     * @return script data to be executed
  //     */
  //    public String getScript(ConfigEntity.Request src) {
  //        if (Objects.isNull(src.getRun())) {
  //            if (!Objects.isNull(src.getFile())){
  //                return getFileContent(src.getFile());
  //            }
  //
  //            logger.fatal(new ScriptDataException().getMessage());
  //        }
  //
  //        return src.getRun();
  //    }

  public InputStream openScriptFiles() {
    InputStream targetStream = new FileInputStream(initialFile);
  }

  /**
   * Deserializes given in the configuration file credentials into the requested provider type.
   *
   * @return credentials for a requested provider
   * @param <T> type of the provider
   */
  public <T> T getCredentials() {
    try {
      return (T) parsedConfigFile.getCloud().getCredentials();
    } catch (ClassCastException e) {

    }

    // TODO: add manual validation of field values.
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
