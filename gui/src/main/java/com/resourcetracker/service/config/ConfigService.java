package com.resourcetracker.service.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.resourcetracker.entity.ConfigEntity;
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
     * Opens YAML configuration file
     */
    public ConfigService(@Value("${config.root}") String configRootPath, @Value("${config.file}") String configFilePath) {
        try {
            configFile = new FileInputStream(Paths.get(System.getProperty("user.home"), configRootPath, configFilePath).toString());
        } catch (FileNotFoundException e) {
            logger.fatal(e.getMessage());
        }
    }

    /**
     * Processes configuration file
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
