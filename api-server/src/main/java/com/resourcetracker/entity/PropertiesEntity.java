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

  @ConfigProperty(name = "config.root")
  String configRootPath;

  @ConfigProperty(name = "config.file")
  String configFilePath;

  @ConfigProperty(name = "kafka.topic")
  String kafkaTopic;

  @ConfigProperty(name = "kafka.partitions")
  String kafkaPartitions;

  @ConfigProperty(name = "terraform.directory")
  String terraformDirectory;

  @ConfigProperty(name = "workspace.directory")
  String workspaceDirectory;

  @ConfigProperty(name = "workspace.variables-file.name")
  String workspaceVariablesFileName;

  @ConfigProperty(name = "workspace.internal-config.name")
  String workspaceInternalConfigFileName;

  @ConfigProperty(name = "remote-storage.name")
  String remoteStorageName;

  @ConfigProperty(name = "git.commit.id.abbrev")
  String gitCommitId;

  @ConfigProperty(name = "resourcetracker-agent.image")
  String resourceTrackerAgentImage;

  @ConfigProperty(name = "resourcetracker-agent.context.alias")
  String resourceTrackerAgentContextAlias;

  @ConfigProperty(name = "resourcetracker-kafka.image")
  String resourceTrackerKafkaImage;

  @ConfigProperty(name = "resourcetracker-kafka.image.version")
  String resourceTrackerKafkaImageVersion;

  @ConfigProperty(name = "resourcetracker-kafka.host.alias")
  String resourceTrackerKafkaHostAlias;

  @ConfigProperty(name = "resourcetracker-kafka.create-topics.alias")
  String resourceTrackerKafkaCreateTopicsAlias;

  @ConfigProperty(name = "resourcetracker-kafka.partitions.alias")
  String resourceTrackerKafkaPartitionsAlias;

  @ConfigProperty(name = "resourcetracker-kafka.port")
  Integer resourceTrackerKafkaPort;

  @ConfigProperty(name = "aws.default.region")
  String awsDefaultRegion;

  @ConfigProperty(name = "aws.readiness.period")
  Integer awsReadinessPeriod;

  @ConfigProperty(name = "aws.resourcetracker-init.name")
  String awsResourceTrackerInitName;

  @ConfigProperty(name = "aws.resourcetracker-agent.name")
  String awsResourceTrackerAgentName;

  @ConfigProperty(name = "aws.resourcetracker-kafka.name")
  String awsResourceTrackerKafkaName;

  @ConfigProperty(name = "aws.resourcetracker-common.family")
  String awsResourceTrackerCommonFamily;

  @ConfigProperty(name = "aws.resourcetracker-common.cpu.units")
  String awsResourceTrackerCommonCPUUnits;

  @ConfigProperty(name = "aws.resourcetracker-common.memory.units")
  String awsResourceTrackerCommonMemoryUnits;

  @ConfigProperty(name = "aws.resourcetracker-execution.role")
  String awsResourceTrackerExecutionRole;
}
