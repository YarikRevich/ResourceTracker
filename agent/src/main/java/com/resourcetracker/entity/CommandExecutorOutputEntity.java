package com.resourcetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents gathered output of the executed command.
 */
@Getter
@AllArgsConstructor(staticName = "of")
public class CommandExecutorOutputEntity {
    String normalOutput;

    String errorOutput;
}
