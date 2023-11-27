package com.resourcetracker.service.config;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.nio.file.Paths;

import com.resourcetracker.model.AWSCredentials;
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
     * Converts given credentials CSV file to a certain object.
     * @return converted credentials.
     */
    public <T> T getConvertedCredentials() {
        return null;
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
