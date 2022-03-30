package com.resourcetracker.tools.params;

import java.util.regex.Pattern;
import java.util.TreeMap;
import java.util.Map;

public class Params {

    /**
     * Commands
     */
    private static Param<Boolean> start = new BooleanParam(false);
    private static Param<Boolean> stop = new BooleanParam(false);
    private static Param<Boolean> validate = new BooleanParam(false);
    
    /**
     * Flags
     */
    private static Param<Boolean> help = new BooleanParam(false);


    private static Map<String, Param<Boolean>> availableBooleanCommands = new TreeMap<String, Param<Boolean>>() {
        {
            put("start", start);
            put("stop", stop);
        };
    };

    private static Map<String, Param<Boolean>> availableBooleanFlags = new TreeMap<String, Param<Boolean>>() {
        {
            put("help", help);
        };
    };

    /**
     * Parses commands and flags set via CLI
     * 
     * @param args Set params via CLI
     */
    public static void parse(String[] args) {
        for (String arg : args) {
            if (Pattern.matches("(--.[a-z]*)", arg)) {
                Param<Boolean> obj = availableBooleanFlags.get(arg.replaceAll("--", ""));
                if (obj != null) {
                    obj.setValue(true);
                }
            } else {
                Param<Boolean> obj = availableBooleanCommands.get(arg);
                if (obj != null) {
                    obj.setValue(true);
                }
            }
        }
    }

    public static void ifStartDo(ParamCallback callback) throws Exception {
        if (start.getValue()){
            callback.call();
            System.exit(0); 
        }
    }

    public static void ifStopDo(ParamCallback callback) throws Exception {
        if (stop.getValue()){
            callback.call();
            System.exit(0); 
        }
    }

    public static void ifValidateDo(ParamCallback callback) throws Exception {
        if (validate.getValue()){
            callback.call();
            System.exit(0); 
        }
    }

    public static Param<Boolean> getHelp() {
        return help;
    }
}
