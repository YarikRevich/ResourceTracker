package com.resourcetracker.tools.params;

import java.util.regex.Pattern;
import java.util.TreeMap;
import java.util.Map;
import com.resourcetracker.tools.utils.Mode;

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
            put("validate", validate);
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
                // System.out.println(obj);
                // System.out.println(availableBooleanCommands);
                // System.out.println(arg.replaceAll("--", ""));
                if (obj != null) {
                    obj.setValue(true);
                }
            }
        }
    }

    public static boolean ifStartDo(ParamCallback callback) throws Exception {
        if (start.getValue()) {
            callback.call();
            if (!Mode.isTest()) {
                System.exit(0);
            }
            return true;
        }
        return false;
    }

    public static boolean ifStopDo(ParamCallback callback) throws Exception {
        if (stop.getValue()) {
            callback.call();
            if (!Mode.isTest()) {
                System.exit(0);
            }
            return true;
        }
        return false;
    }

    public static boolean ifValidateDo(ParamCallback callback) throws Exception {
        if (validate.getValue()) {
            callback.call();
            if (!Mode.isTest()) {
                System.exit(0);
            }
            return true;
        }
        return false;
    }

    public static Param<Boolean> getHelp() {
        return help;
    }
}
