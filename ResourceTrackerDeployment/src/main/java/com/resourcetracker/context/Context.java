package com.resourcetracker.context;

public class Context {
    public static void init() {
        String context = System.getenv("context");
    }

    /**
     * 
     * @return frequency in milliseconds
     */
    public static int getReportFrequency(){
        return 0;
    }
}
