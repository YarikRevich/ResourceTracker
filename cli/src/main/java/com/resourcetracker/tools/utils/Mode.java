package com.resourcetracker.tools.utils;

public class Mode {
    /**
     * Checks if there is imported a 'org.junit.Test' package
     * 
     * @return if test is running
     */
    public static boolean isTest() {
        boolean result = true;
        try {
            Class.forName("org.junit.Test");
        } catch (ClassNotFoundException e) {
            result = false;
        }
        return result;
    }
}
