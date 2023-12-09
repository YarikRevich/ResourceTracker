package com.resourcetracker.resource;

import com.resourcetracker.api.ValidationResourceApi;
import com.resourcetracker.converter.SecretsConverter;
import com.resourcetracker.dto.AWSSecretsDto;
import com.resourcetracker.model.Provider;
import com.resourcetracker.model.ValidationScriptApplicationResult;
import com.resourcetracker.model.ValidationSecretsApplicationResult;
import com.resourcetracker.model.ValidationSecretsApplicationResultSecrets;
import com.resourcetracker.service.vendor.VendorFacade;
import com.resourcetracker.service.vendor.aws.AWSVendorService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.InputStream;
import lombok.SneakyThrows;

/** Contains implementation of ValidationResource. */
@ApplicationScoped
public class ValidationResource implements ValidationResourceApi {
  @Inject VendorFacade vendorFacade;

  /**
   * Implementation for declared in OpenAPI configuration v1CredentialsAcquirePost method.
   *
   * @param provider remote cloud provider to be selected for processing.
   * @param _fileInputStream given file to be processed.
   * @return Credentials validation result.
   */
  @Override
  @SneakyThrows
  public ValidationSecretsApplicationResult v1SecretsAcquirePost(
      Provider provider, InputStream _fileInputStream) {
    return switch (provider) {
      case AWS -> {
        AWSSecretsDto secrets =
            SecretsConverter.convert(AWSSecretsDto.class, _fileInputStream).get(1);

        yield ValidationSecretsApplicationResult.of(
            vendorFacade.isCredentialsValid(
                provider, AWSVendorService.getAWSCredentialsProvider(secrets)),
            ValidationSecretsApplicationResultSecrets.of(
                secrets.getAccessKey(), secrets.getSecretKey()));
      }
    };
  }

  /**
   * Implementation for declared in OpenAPI configuration v1ScriptAcquirePost method.
   *
   * @param _fileInputStream given file to be processed.
   * @return Script validation result.
   */
  @Override
  public ValidationScriptApplicationResult v1ScriptAcquirePost(InputStream _fileInputStream) {
    //        ScriptConverter.convert(_fileInputStream);
    return ValidationScriptApplicationResult.of(true, null);
  }
}
