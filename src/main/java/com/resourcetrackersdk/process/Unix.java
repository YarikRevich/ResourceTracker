package com.resourcetrackersdk.process;

import com.resourcetrackersdk.processcreator.ProcessCreator;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Unix {
    final static Logger logger = LogManager.getLogger(Unix.class);

    public static void start() {
        final String command1 = "cd src/main/java/resources/resourcetracker.service /etc/systemd/system";
        final String command2 = "systemctl start resourcetracker.service";
        final String command3 = "systemctl enable resourcetracker.service";

        ProcessCreator processCreator = new ProcessCreator(command1, command2, command3);
        processCreator.start();

        if (processCreator.isError()) {
            logger.error(processCreator.getStderr());
        }
    }

    public static void stop() {
        final String command1 = "systemctl stop resourcetracker.service";

        ProcessCreator processCreator = new ProcessCreator(command1);
        processCreator.start();

        if (processCreator.isError()) {
            logger.error(processCreator.getStderr());
        }
    }

    public static boolean isOk() {
        final String command1 = "systemctl is-active resourcetracker.service";

        ProcessCreator processCreator = new ProcessCreator(command1);
        processCreator.start();

        if (processCreator.isError()) {
            logger.error(processCreator.getStderr());
        }
        String stdout = processCreator.getStdout();
        switch (stdout) {
            case ProcessCreator.ACTIVE_UNIX:
                return true;
            case ProcessCreator.INACTIVE_UNIX:
                return false;
            default:
                return false;
        }
    }
}
