package com.resourcetracker.service.integration;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.resourcetracker.service.config.ConfigService;
import io.quarkus.runtime.annotations.StaticInitSafe;
import jakarta.validation.constraints.Null;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Provides access to external config source used as a source of Git info.
 */
@StaticInitSafe
public class GitBuildConfigSourceService implements ConfigSource {
    private static final Logger logger = LogManager.getLogger(GitBuildConfigSourceService.class);

    private final Properties properties;

    public GitBuildConfigSourceService() {
        this.properties = new Properties();

        String rootPath = null;

        try {
            rootPath = Thread
                    .currentThread()
                    .getContextClassLoader()
                    .getResource("")
                    .getPath();
        } catch (NullPointerException e){
            logger.fatal(e.getMessage());
        }

        System.out.println(rootPath);

        URL gitBuildProperties = ClassLoader.getSystemResource(Paths.get(rootPath, "application.properties").toString());

        System.out.println(gitBuildProperties);
//        InputStream gitBuildPropertiesStream = null;
//        try {
//            gitBuildPropertiesStream = gitBuildProperties.openStream();
//        } catch (IOException e) {
//            logger.fatal(e.getMessage());
//        }
//
//        try {
//            properties.load(gitBuildPropertiesStream);
//        } catch (IOException e) {
//            logger.fatal(e.getMessage());
//        }

        System.out.println(properties);
    }

    /**
     * @return
     */
    @Override
    public Map<String, String> getProperties() {
        return ConfigSource.super.getProperties();
    }

    /**
     * @return
     */
    @Override
    public Set<String> getPropertyNames() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public int getOrdinal() {
        return ConfigSource.super.getOrdinal();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public String getValue(String s) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public String getName() {
        return null;
    }
}
