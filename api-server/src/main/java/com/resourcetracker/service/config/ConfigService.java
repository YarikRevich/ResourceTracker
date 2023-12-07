package com.resourcetracker.service.config;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.resourcetracker.exception.SecretsConversionException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.resourcetracker.entity.ConfigEntity;

/**
 * Service for processing configuration file
 *
 * @author YarikRevich
 */
@ApplicationScoped
public class ConfigService {
    private static final Logger logger = LogManager.getLogger(ConfigService.class);

    private InputStream configFile;

    private ConfigEntity parsedConfigFile;

    /**
     * Default constructor, which opens configuration file at the given path.
     */
    @Inject
    public ConfigService(@ConfigProperty(name = "config.root") String configRootPath, @ConfigProperty(name = "config.file") String configFilePath) {
        try {
            configFile = new FileInputStream(Paths.get(System.getProperty("user.home"), configRootPath, configFilePath).toString());
        } catch (FileNotFoundException e) {
            logger.fatal(e.getMessage());
        }
    }

    /**
     * Reads configuration from the opened configuration file
     * using mapping with a configuration entity.
     */
    @PostConstruct
    private void process() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory())
                .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectReader reader = mapper.reader().forType(new TypeReference<ConfigEntity>() {
        });

        try {
            parsedConfigFile = reader.<ConfigEntity>readValues(configFile).readAll().getFirst();
        } catch (IOException e) {
            logger.fatal(e.getMessage());
        }
    }

    /**
     * Extracts data from the given script file.
     * @param src path to the script file
     * @return data from the given script file
     */
    private String getFileContent(String src) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(src));
        } catch (FileNotFoundException e) {
            logger.fatal(e.getMessage());
        }

        StringBuilder result = new StringBuilder();

        String currentLine = null;
        try {
            currentLine = reader.readLine();
        } catch (IOException e) {
            logger.fatal(e.getMessage());
        }

        while (currentLine != null) {
            result.append(currentLine);

            try {
                currentLine = reader.readLine();
            } catch (IOException e) {
                logger.fatal(e.getMessage());
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            logger.fatal(e.getMessage());
        }

        return result.toString();
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
