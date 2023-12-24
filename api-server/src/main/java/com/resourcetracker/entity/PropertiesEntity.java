package com.resourcetracker.entity;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/** Exposes access to properties setup to be used for further configuration. */
@Getter
@ApplicationScoped
public class PropertiesEntity {
  @ConfigProperty(name = "quarkus.application.version")
  String applicationVersion;

  @ConfigProperty(name = "quarkus.http.host")
  String applicationHost;

  @ConfigProperty(name = "quarkus.http.port")
  Integer applicationPort;

  @ConfigProperty(name = "terraform.directory")
  String terraformDirectory;

  @ConfigProperty(name = "workspace.directory")
  String workspaceDirectory;

  @ConfigProperty(name = "workspace.variables-file.name")
  String workspaceVariablesFileName;

  @ConfigProperty(name = "remote-storage.name")
  String remoteStorageName;

  @ConfigProperty(name = "git.commit.id.abbrev")
  String gitCommitId;

  @ConfigProperty(name = "aws.default.region")
  String awsDefaultRegion;
}
