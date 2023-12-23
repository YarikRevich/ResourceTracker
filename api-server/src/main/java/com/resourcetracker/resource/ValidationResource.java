package com.resourcetracker.resource;

import com.resourcetracker.api.ValidationResourceApi;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.model.ValidationScriptApplication;
import com.resourcetracker.model.ValidationScriptApplicationResult;
import com.resourcetracker.model.ValidationSecretsApplication;
import com.resourcetracker.model.ValidationSecretsApplicationResult;
import com.resourcetracker.service.vendor.VendorFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.SneakyThrows;

/** Contains implementation of ValidationResource. */
@ApplicationScoped
public class ValidationResource implements ValidationResourceApi {
  @Inject PropertiesEntity properties;

  @Inject VendorFacade vendorFacade;

  /**
   * Implementation for declared in OpenAPI configuration v1CredentialsAcquirePost method.
   *
   * @param validationSecretsApplication given file to be processed.
   * @return Credentials validation result.
   */
  @Override
  @SneakyThrows
  public ValidationSecretsApplicationResult v1SecretsAcquirePost(
      ValidationSecretsApplication validationSecretsApplication) {
    return vendorFacade.isCredentialsValid(validationSecretsApplication);
  }

  /**
   * Implementation for declared in OpenAPI configuration v1ScriptAcquirePost method.
   *
   * @param validationScriptApplication
   * @return Script validation result.
   */
  @Override
  public ValidationScriptApplicationResult v1ScriptAcquirePost(
      ValidationScriptApplication validationScriptApplication) {
    return ValidationScriptApplicationResult.of(true);
  }
}
