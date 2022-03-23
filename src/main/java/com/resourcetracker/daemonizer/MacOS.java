package com.resourcetracker.daemonizer;

public class MacOS {
    public static void start(){
        final String command1 = "launchctl load src/main/java/resources/resourcetracker.plist && ";
        final String command2 = "launchctl start resourcetracker";
        new DaemonizingProcessBuilder(command1, command2).start();
    }
    
    public static void stop(){
        final String command1 = "launchctl stop resourcetracker";
        new DaemonizingProcessBuilder(command1).start();
    }   
}
