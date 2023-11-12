package com.resourcetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ScriptExecCommandInputEntity {
    String script;
}
