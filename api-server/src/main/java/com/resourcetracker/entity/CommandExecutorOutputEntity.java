package com.resourcetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class CommandExecutorOutputEntity {
    String normalOutput;

    String errorOutput;
}
