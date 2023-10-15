package com.resourcetracker.service.config;

        import java.io.FileInputStream;
        import java.io.FileNotFoundException;
        import java.io.IOException;
        import java.io.InputStream;
        import java.nio.file.Paths;

        import org.apache.commons.io.IOUtils;
        import org.apache.logging.log4j.LogManager;
        import org.apache.logging.log4j.Logger;
        import org.springframework.boot.context.event.ApplicationReadyEvent;
        import org.springframework.context.event.EventListener;
        import org.springframework.stereotype.Component;

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
@Component
public class ConfigService {
    private static final Logger logger = LogManager.getLogger(ConfigService.class);

    public static final String DEFAULT_CONFIG_FILE_PATH = Paths.get(System.getProperty("user.home"), "resourcetracker.yaml").toString();

    private final InputStream configFile;

    private ConfigEntity parsedConfigFile;

    /**
     * Opens YAML configuration file
     */
    public ConfigService() {
        try {
            configFile = new FileInputStream(DEFAULT_CONFIG_FILE_PATH);
        } catch (FileNotFoundException e) {
            logger.fatal(e.getMessage());
        }
    }

    /**
     * Converts json source to InputStream
     * @param src
     */
    public ConfigService(String src){
        configFile = IOUtils.toInputStream(src, "UTF-8");
    }

    /**
     * Processes configuration file
     */
    @EventListener(ApplicationReadyEvent.class)
    public void process() {
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
}
