package com.resourcetrackersdk.process;

import com.resourcetrackersdk.processcreator.ProcessCreator;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MacOS {
    final static Logger logger = LogManager.getLogger(MacOS.class);

    public static void start(){
        final String command1 = "launchctl load src/main/java/resources/resourcetracker.plist";
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
        final String command1 = "launchctl list | grep com.resourcetrackersdk.Tracker";

        ProcessCreator processCreator = new ProcessCreator(command1);
        processCreator.start();

        if (processCreator.isError()) {
            logger.error(processCreator.getStderr());
        }
        if (processCreator.isStdout()){
            return true;
        }
        return false;
    }
}
