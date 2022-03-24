package com.resourcetracker.process;

import com.resourcetracker.processcreator.ProcessCreator;

public class MacOS {
    final Logger logger = LogManager.getLogger(MacOS.class);

    public static void start(){
        final String command1 = "launchctl load src/main/java/resources/resourcetracker.plist && ";
        final String command2 = "launchctl start resourcetracker";

        ProcessCreator processCreator = new ProcessCreator(command1, command2);
        processCreator.start();

        if (processCreator.isError()) {
            logger.error(processCreator.getStderr());
        }
    }

    public static void stop(){
        final String command1 = "launchctl stop resourcetracker";
        
        ProcessCreator processCreator = new ProcessCreator(command1);
        processCreator.start();

        if (processCreator.isError()) {
            logger.error(processCreator.getStderr());
        }
    }   

    public static  boolean isOk(){
        return false;
    }
}
