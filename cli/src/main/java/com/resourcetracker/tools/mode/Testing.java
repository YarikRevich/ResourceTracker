package com.resourcetracker.tools.mode;

public class Testing {
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
