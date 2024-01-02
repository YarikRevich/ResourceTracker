package com.resourcetracker.service.terraform.workspace.facade;

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
}
