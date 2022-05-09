package com.resourcetracker.config;

import java.nio.file.*;

/**
 * Describes path to config which depends on
 * the OS type
 *
 * @author YarikRevich
 *
 */
public class ConfigPath {
    private static String configPath;

    static {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")){
            configPath = Paths.get("/usr/local/etc/resourcetracker.yaml").toString();
        }else if (os.contains("Linux") || os.contains("Mac OS X")) {
            configPath = Paths.get("/usr/local/etc/resourcetracker.yaml").toString();
        }
    }

    public static String getConfigPath(){
        return configPath;
    }
}
