package com.resourcetracker.service.command.internal.readiness.provider.aws;

import com.resourcetracker.converter.CredentialsConverter;
import com.resourcetracker.dto.ValidationScriptApplicationDto;
import com.resourcetracker.dto.ValidationSecretsApplicationDto;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.model.*;
import com.resourcetracker.service.client.command.ReadinessCheckClientCommandService;
import com.resourcetracker.service.client.command.ScriptAcquireClientCommandService;
import com.resourcetracker.service.client.command.SecretsAcquireClientCommandService;
import com.resourcetracker.service.command.ICommand;
import com.resourcetracker.service.command.internal.readiness.ReadinessCheckInternalCommandService;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.desktop.SystemSleepEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class AWSReadinessCheckInternalCommandService implements ICommand {
    private static final Logger logger =
            LogManager.getLogger(AWSReadinessCheckInternalCommandService.class);

    @Autowired private ConfigService configService;

    @Autowired private SecretsAcquireClientCommandService secretsAcquireClientCommandService;

    @Autowired
    private ReadinessCheckClientCommandService readinessCheckClientCommandService;

    /**
     * @see ICommand
     */
    @Override
    public void process() throws ApiServerException {
            ConfigEntity.Cloud.AWSCredentials credentials =
            CredentialsConverter.convert(
                    configService.getConfig().getCloud().getCredentials(),
                    ConfigEntity.Cloud.AWSCredentials.class);

        ValidationSecretsApplicationDto validationSecretsApplicationDto =
            ValidationSecretsApplicationDto.of(Provider.AWS, credentials.getFile());

        ValidationSecretsApplicationResult validationSecretsApplicationResult =
            secretsAcquireClientCommandService.process(validationSecretsApplicationDto);

        if (validationSecretsApplicationResult.getValid()) {
            CredentialsFields credentialsFields =
                    CredentialsFields.of(
                            AWSSecrets.of(
                                    validationSecretsApplicationResult.getSecrets().getAccessKey(),
                                    validationSecretsApplicationResult.getSecrets().getSecretKey()),
                            credentials.getRegion());

            ReadinessCheckApplication readinessCheckApplication =
                    ReadinessCheckApplication.of(Provider.AWS, credentialsFields);

            ReadinessCheckResult readinessCheckResult =
                    readinessCheckClientCommandService.process(readinessCheckApplication);

            switch (readinessCheckResult.getStatus()) {
                case UP -> System.out.println("API Server is up");
                case DOWN -> System.out.println("API Server is down");
            }
        }
    }
//
//    ConfigEntity.Cloud.AWSCredentials credentials =
//            CredentialsConverter.convert(
//                    configService.getConfig().getCloud().getCredentials(),
//                    ConfigEntity.Cloud.AWSCredentials.class);
//
//    ValidationSecretsApplicationDto validationSecretsApplicationDto =
//            ValidationSecretsApplicationDto.of(Provider.AWS, credentials.getFile());
//
//    ValidationSecretsApplicationResult validationSecretsApplicationResult =
//            secretsAcquireClientCommandService.process(validationSecretsApplicationDto);
//
//        if (validationSecretsApplicationResult.getValid()) {
//        List<DeploymentRequest> requests =
//                configService.getConfig().getRequests().stream()
//                        .map(
//                                element -> {
//                                    try {
//                                        return DeploymentRequest.of(
//                                                element.getName(),
//                                                Files.readString(Paths.get(element.getFile())),
//                                                element.getFrequency());
//                                    } catch (IOException e) {
//                                        throw new RuntimeException(e);
//                                    }
//                                })
//                        .toList();
//
//        ValidationScriptApplicationDto validationScriptApplicationDto =
//                ValidationScriptApplicationDto.of(
//                        requests.stream().map(DeploymentRequest::getScript).toList());
//
//        ValidationScriptApplicationResult validationScriptApplicationResult =
//                scriptAcquireClientCommandService.process(validationScriptApplicationDto);
//
//        if (validationScriptApplicationResult.getValid()) {
//            CredentialsFields credentialsFields =
//                    CredentialsFields.of(
//                            AWSSecrets.of(
//                                    validationSecretsApplicationResult.getSecrets().getAccessKey(),
//                                    validationSecretsApplicationResult.getSecrets().getSecretKey()),
//                            credentials.getRegion());
//
//
//
//
//
//
//

}
