package com.resourcetracker.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.List;

@Getter
public class ConfigEntity {
    @NotBlank
    List<Request> requests;

    @Getter
    public static class Request {
        @NotBlank
        String script;

        @NotBlank
        String frequency;
    }
}
