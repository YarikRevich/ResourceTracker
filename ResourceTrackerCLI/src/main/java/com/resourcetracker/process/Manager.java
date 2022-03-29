package com.resourcetracker.process;

/**
 * Manages demonizing process dependeing on the
 * used os type
 * 
 * @author YarikRevich
 */
public class Manager {
    public static void start() {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            Windows.start();
        } else if (os.contains("Mac OS X")) {
            MacOS.start();
        } else if (os.contains("Linux") || os.contains("Other")) {
            Unix.start();
        }
    }

    public static void stop() {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            Windows.stop();
        } else if (os.contains("Mac OS X")) {
            MacOS.stop();
        } else if (os.contains("Linux") || os.contains("Other")) {
            Unix.stop();
        }
    }

    public static boolean isOk(){
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            return Windows.isOk();
        } else if (os.contains("Mac OS X")) {
            return MacOS.isOk();
        } else if (os.contains("Linux") || os.contains("Other")) {
            return Unix.isOk();
        }
        return false;
    }
}
