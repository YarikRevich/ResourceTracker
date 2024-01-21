package com.resourcetracker.service.command.external.stop.provider.aws;

import com.resourcetracker.converter.CredentialsConverter;
import com.resourcetracker.dto.ValidationSecretsApplicationDto;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.exception.CloudCredentialsValidationException;
import com.resourcetracker.model.AWSSecrets;
import com.resourcetracker.model.CredentialsFields;
import com.resourcetracker.model.DestructionRequest;
import com.resourcetracker.model.Provider;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.model.ValidationSecretsApplicationResult;
import com.resourcetracker.service.client.command.DestroyClientCommandService;
import com.resourcetracker.service.client.command.SecretsAcquireClientCommandService;
import com.resourcetracker.service.command.common.ICommand;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.visualization.state.VisualizationState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** */
@Service
public class AWSStopExternalCommandService implements ICommand {
  private static final Logger logger = LogManager.getLogger(AWSStopExternalCommandService.class);

  @Autowired private ConfigService configService;

  @Autowired private DestroyClientCommandService destroyClientCommandService;

  @Autowired private SecretsAcquireClientCommandService secretsAcquireClientCommandService;

  @Autowired private VisualizationState visualizationState;

  /**
   * @see ICommand
   */
  @Override
  public void process() throws ApiServerException {
    visualizationState.getLabel().pushNext();

    ConfigEntity.Cloud.AWSCredentials credentials =
        CredentialsConverter.convert(
            configService.getConfig().getCloud().getCredentials(),
            ConfigEntity.Cloud.AWSCredentials.class);

    ValidationSecretsApplicationDto validationSecretsApplicationDto =
        ValidationSecretsApplicationDto.of(Provider.AWS, credentials.getFile());

    ValidationSecretsApplicationResult validationSecretsApplicationResult =
        secretsAcquireClientCommandService.process(validationSecretsApplicationDto);

    if (validationSecretsApplicationResult.getValid()) {
      visualizationState.getLabel().pushNext();

      CredentialsFields credentialsFields =
          CredentialsFields.of(
              AWSSecrets.of(
                  validationSecretsApplicationResult.getSecrets().getAccessKey(),
                  validationSecretsApplicationResult.getSecrets().getSecretKey()),
              credentials.getRegion());

      TerraformDestructionApplication terraformDestructionApplication =
          TerraformDestructionApplication.of(
              configService.getConfig().getRequests().stream()
                  .map(element -> DestructionRequest.of(element.getName()))
                  .toList(),
              Provider.AWS,
              credentialsFields);

      destroyClientCommandService.process(terraformDestructionApplication);

      visualizationState.getLabel().pushNext();
    } else {
      logger.fatal(new CloudCredentialsValidationException().getMessage());
    }
  }
}
