package com.resourcetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ScriptExecCommandInputDto {
    String script;
}
