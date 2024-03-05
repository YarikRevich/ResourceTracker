package com.resourcetracker.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/** Represents configuration model used for ResourceTracker API Server operations. */
@Getter
@ApplicationScoped
public class ConfigEntity {
    /** Represents API Server configuration used for further connection establishment. */
    @Getter
    public static class Connection {
        @NotEmpty
        public String host;

        @NotNull
        public Integer port;
    }

    @Valid
    @NotNull
    @JsonProperty("connection")
    public Connection connection;
}
