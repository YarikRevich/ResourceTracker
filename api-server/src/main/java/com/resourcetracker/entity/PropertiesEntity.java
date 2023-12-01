package com.resourcetracker.entity;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Exposes access to properties setup in the project
 * to provide further configuration.
 */
@Getter
@ApplicationScoped
public class PropertiesEntity {
    @ConfigProperty(name = "quarkus.application.version")
    String applicationVersion;

    @ConfigProperty(name = "terraform.directory")
    String terraformDirectory;

    @ConfigProperty(name = "remote-storage.name")
    String remoteStorageName;

//    @ConfigProperty(name = "git.branch")
    String gitCommitId;
}
