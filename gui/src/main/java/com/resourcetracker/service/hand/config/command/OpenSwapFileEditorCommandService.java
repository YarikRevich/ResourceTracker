package com.resourcetracker.service.hand.config.command;

import process.SProcess;
import process.SProcessExecutor;

import java.nio.file.Paths;

/**
 * Represents command, which is responsible for a startup of the given swap file editor.
 */
public class OpenSwapFileEditorCommandService extends SProcess {
    private final String command;
    private final SProcessExecutor.OS osType;

    public OpenSwapFileEditorCommandService(String swapRootPath, String swapUserFilePath) {
        this.osType = SProcessExecutor.getCommandExecutor().getOSType();

        this.command =
                switch (osType) {
                    case MAC -> String.format(
                            "open -W -a TextEdit %s",
                            Paths.get(System.getProperty("user.home"), swapRootPath, swapUserFilePath));
                    case WINDOWS, UNIX, ANY -> null;
                };
    }

    @Override
    public String getCommand() {
        return command;
    }

    @Override
    public SProcessExecutor.OS getOSType() {
        return osType;
    }
}