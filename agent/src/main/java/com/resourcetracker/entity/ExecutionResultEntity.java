package com.resourcetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor(staticName = "of")
public class ExecutionResultEntity {
    String error;
}
