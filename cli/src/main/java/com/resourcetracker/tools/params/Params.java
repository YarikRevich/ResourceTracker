package com.resourcetracker.tools.params;

import java.util.regex.Pattern;
import java.util.TreeMap;
import java.util.Map;

import com.resourcetracker.tools.utils.Mode;
import com.resourcetracker.tools.exceptions.ParamException;

public class Params {

	/**
	 * Commands
	 */
	private static Param<Boolean> start = new BooleanParam(false).withPositionalArgs();
	private static Param<Boolean> stop = new BooleanParam(false).withPositionalArgs();
	private static Param<Boolean> validate = new BooleanParam(false);
	private static Param<Boolean> logs = new BooleanParam(false).withPositionalArgs();

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
	public static void parse(String[] args) throws Exception {
		boolean waitForPositionalArg;
		BooleanParam waitForPositionArgAttachedParam = null;
		for (String arg : args) {
			if (Pattern.matches("(--.[a-z]*)", arg)) {
				if (waitForPositionalArg) {
					throw new ParamException("You cannot use flags passing commands in the same request");
				}

				Param<Boolean> obj = availableBooleanFlags.get(arg.replaceAll("--", ""));

				if (obj != null) {
					obj.setValue(true);
				}
			} else {
				if (waitForPositionalArg) {
					throw new ParamException("You cannot use multiple commands in one request");
				}

				Param<Boolean> obj = availableBooleanCommands.get(arg);
				if (obj != null) {
					obj.setValue(true);
				}

				if (obj.isWithPositionalArgs()) {
					waitForPositionalArg = true;
					waitForPositionArgAttachedParam = obj;
				}
			}

			if (waitForPositionalArg) {
				waitForPositionArgAttachedParam.setPositionalArg(arg);
			}
		}
	}

	public static boolean ifStartDo(ParamCallback callback) throws Exception {
		if (start.getValue()) {
			// if (Config.isValid()) {
				callback.call(start.getPositionalArgs());
				if (!Mode.isTest()) {
					System.exit(0);
				}
				return true;
			// }
		}
		return false;
	}

	public static boolean ifStopDo(ParamCallback callback) throws Exception {
		if (stop.getValue()) {
			// if (Config.isValid()) {
				callback.call(stop.getPositionalArgs());
				if (!Mode.isTest()) {
					System.exit(0);
				}
				return true;
			// }
		}
		return false;
	}

	public static boolean ifValidateDo(ParamCallback callback) throws Exception {
		if (validate.getValue()) {
			if (Config.isValid()) {
				callback.call(null);
				if (!Mode.isTest()) {
					System.exit(0);
				}
				return true;
			}
		}
		return false;
	}

	public static boolean ifLogDo(ParamCallback callback) throws Exception {
		if (log.getValue()) {
			// if (Config.isValid()) {
				callback.call(log.getPositionalArgs());
				if (!Mode.isTest()) {
					System.exit(0);
				}
				return true;
			// }
		}
		return false;
	}

	public static Param<Boolean> getHelp() {
		return help;
	}
}
