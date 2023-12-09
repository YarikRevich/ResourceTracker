package com.resourcetracker.resource;

import com.resourcetracker.api.InfoResourceApi;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.model.ApplicationExternalApiInfoResult;
import com.resourcetracker.model.ApplicationInfoResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/** Contains implementation of InfoResourceApi. */
@ApplicationScoped
public class InfoResource implements InfoResourceApi {
  @Inject PropertiesEntity properties;

  /**
   * Implementation for declared in OpenAPI configuration v1CredentialsAcquirePost method.
   *
   * @return Application information result.
   */
  @Override
  public ApplicationInfoResult v1InfoVersionGet() {
    return ApplicationInfoResult.of(
        ApplicationExternalApiInfoResult.of(
            properties.getApplicationVersion(), properties.getGitCommitId()));
  }
}
