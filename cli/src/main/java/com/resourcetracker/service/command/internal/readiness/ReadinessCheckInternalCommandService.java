package com.resourcetracker.service.command.internal.readiness;

import com.resourcetracker.converter.CredentialsConverter;
import com.resourcetracker.dto.ValidationScriptApplicationDto;
import com.resourcetracker.dto.ValidationSecretsApplicationDto;
import com.resourcetracker.entity.ConfigEntity;
import com.resourcetracker.exception.ApiServerException;
import com.resourcetracker.model.*;
import com.resourcetracker.service.client.command.ReadinessCheckClientCommandService;
import com.resourcetracker.service.command.ICommand;
import com.resourcetracker.service.command.internal.readiness.provider.aws.AWSReadinessCheckInternalCommandService;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ReadinessCheckInternalCommandService implements ICommand {
    @Autowired private ConfigService configService;

    @Autowired private AWSReadinessCheckInternalCommandService awsReadinessCheckInternalCommandService;

    /**
     * @see ICommand
     */
    @Override
    public void process() throws ApiServerException {
        switch (configService.getConfig().getCloud().getProvider()) {
            case AWS -> awsReadinessCheckInternalCommandService.process();
        }
    }
}
