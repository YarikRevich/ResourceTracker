package com.resourcetrackersdk.config;

import java.nio.file.*;

/**
 * Describes path to config which depends on
 * the OS type
 * 
 * @author YarikRevich
 *
 */
public record ConfigPath(Path configPath) {
    public ConfigPath {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")){
            configPath = Paths.get("/usr/local/etc/resourcetracker.yaml");
        }else if (os.contains("Linux") || os.contains("Mac OS X")) {
            configPath = Paths.get("/usr/local/etc/resourcetracker.yaml");
        }
    }
}
