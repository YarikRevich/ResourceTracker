package com.resourcetracker.service.terraform.workspace.facade;

import com.resourcetracker.entity.InternalConfigEntity;
import com.resourcetracker.exception.*;
import com.resourcetracker.model.CredentialsFields;
import com.resourcetracker.model.Provider;
import com.resourcetracker.service.terraform.workspace.WorkspaceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/** Provides high-level access to workspace operations. */
@ApplicationScoped
public class WorkspaceFacade {
  @Inject WorkspaceService workspaceService;

  /**
   * Creates unit key with the help of the given readiness check application.
   *
   * @param provider given provider.
   * @param credentialsFields given credentials.
   * @return result of the readiness check for the given configuration.
   */
  public String createUnitKey(Provider provider, CredentialsFields credentialsFields) {
    return switch (provider) {
      case AWS -> workspaceService.createUnitKey(
          credentialsFields.getSecrets().getAccessKey(),
          credentialsFields.getSecrets().getSecretKey());
    };
  }

  /**
   * Update Kafka host in internal config file.
   *
   * @param provider given provider.
   * @param credentialsFields given credentials.
   * @throws WorkspaceUnitDirectoryNotFoundException if workspace unit directory was not found.
   * @throws InternalConfigNotFoundException if internal config file was not found.
   * @throws InternalConfigWriteFailureException if internal config file cannot be created.
   */
  public void updateKafkaHost(
      String machineAddress, Provider provider, CredentialsFields credentialsFields)
      throws WorkspaceUnitDirectoryNotFoundException,
          InternalConfigNotFoundException,
          InternalConfigWriteFailureException {
    String workspaceUnitKey = createUnitKey(provider, credentialsFields);

    String workspaceUnitDirectory = workspaceService.getUnitDirectory(workspaceUnitKey);

    InternalConfigEntity internalConfig =
        workspaceService.getInternalConfigFileContent(workspaceUnitDirectory);
    internalConfig.getKafka().setHost(machineAddress);

    workspaceService.createInternalConfigFile(workspaceUnitDirectory, internalConfig);
  }
}
