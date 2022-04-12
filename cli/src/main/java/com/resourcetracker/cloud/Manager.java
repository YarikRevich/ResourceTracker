package com.resourcetracker.cloud;

import org.javatuples.Pair;

import com.resourcetracker.config.*;
import com.resourcetracker.cloud.providers.*;
import com.resourcetracker.tools.exceptions.ConfigException;
import com.resourcetracker.config.Config;

public class Manager {
    static Provider provider;

    static {
        try {
            switch (Config.getCloudProvider()) {
                case AWS:
                    provider = new AWS();
                case GCP:
                    provider = new GCP();
                case AZ:
                    provider = new AZ();
            }
        } catch (ConfigException e) {
            e.printStackTrace();
        }
    }

    public static void start(String context) {
        provider.start(context);
    }

    public static void stop() {
        provider.stop();
    }

    public static boolean isOk() {
        return false;
    }
}
