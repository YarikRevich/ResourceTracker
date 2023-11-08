package com.resourcetracker.service.config;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import com.resourcetracker.exception.CronExpressionException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.support.CronExpression;
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

    @Value("#{environment.RESOURCETRACKER_CONFIG}")
    private String CONFIG_FILE;

    private final InputStream configFile;

    private ConfigEntity parsedConfigFile;

    /**
     * Opens YAML configuration file
     */
    public ConfigService() {
        configFile = IOUtils.toInputStream(CONFIG_FILE, "UTF-8");
    }

    /**
     * Processes configuration file
     */
    @EventListener(ApplicationReadyEvent.class)
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

    public Long getCronExpressionInMilliseconds(String src) {
        CronExpression cronExpression = CronExpression.parse(src);
        LocalDateTime nextExecutionTime = cronExpression.next(LocalDateTime.now());
        if (Objects.isNull(nextExecutionTime)){
            logger.fatal(new CronExpressionException().getMessage());
        }
        LocalDateTime afterNextExecutionTime = cronExpression.next(nextExecutionTime);
        return Duration.between(nextExecutionTime, afterNextExecutionTime).toMillis();
    }

    /**
     * @return Parsed configuration entity
     */
    public ConfigEntity getConfig() {
        return parsedConfigFile;
    }
}
