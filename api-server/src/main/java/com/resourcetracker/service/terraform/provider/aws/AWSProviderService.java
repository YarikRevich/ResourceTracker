package com.resourcetracker.service.terraform.provider.aws;

import com.resourcetracker.entity.AWSCredentialsEntity;
import com.resourcetracker.entity.AWSDeploymentCredentialsEntity;
import com.resourcetracker.entity.CommandExecutorOutputEntity;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.CommandExecutorException;
import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDeploymentApplicationCredentials;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.terraform.provider.aws.command.ApplyCommandService;
import com.resourcetracker.service.terraform.provider.executor.CommandExecutorService;
import com.resourcetracker.service.terraform.provider.IProvider;
import com.resourcetracker.service.terraform.provider.aws.command.DestroyCommandService;
import com.resourcetracker.service.terraform.provider.aws.command.InitCommandService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import process.exceptions.SProcessNotYetStartedException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Represents Terraform provider implementation for AWS vendor.
 */
@ApplicationScoped
public class AWSProviderService implements IProvider {
    @Inject
    PropertiesEntity properties;

    @Inject
    CommandExecutorService commandExecutorService;

    /**
     * @see IProvider
     */
    public String apply(TerraformDeploymentApplication terraformDeploymentApplication) throws TerraformException {
        TerraformDeploymentApplicationCredentials credentials = terraformDeploymentApplication.getCredentials();

//        AWSCredentialsEntity file;
//        try {
//             file = ConfigService.getConvertedCredentials(
//                     AWSCredentialsEntity.class, credentials.getFile()).get(1);
//        } catch (FileNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            throw new TerraformException(e.getMessage());
//        }

        InitCommandService initCommandService = new InitCommandService(
                terraformDeploymentApplication.getCredentials(),
                properties);

        CommandExecutorOutputEntity initCommandOutput;

        try {
            initCommandOutput = commandExecutorService.executeCommand(initCommandService);
        } catch (CommandExecutorException e) {
            throw new TerraformException(e.getMessage());
        }

        String initCommandErrorOutput = initCommandOutput.getErrorOutput();

        if (Objects.nonNull(initCommandErrorOutput)){
            throw new TerraformException(initCommandErrorOutput);
        }

//        ApplyCommandService applyCommandService = new ApplyCommandService();

        return null;
    }

    /**
     * @see IProvider
     */
    public void destroy(TerraformDestructionApplication terraformDestructionApplication) throws TerraformException {
        TerraformDeploymentApplicationCredentials credentials = terraformDestructionApplication.getCredentials();

//        AWSCredentialsEntity file;
//        try {
//            file = ConfigService.getConvertedCredentials(
//                    AWSCredentialsEntity.class, credentials.getFile()).get(1);
//        } catch (FileNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//            throw new TerraformException(e.getMessage());
//        }

        DestroyCommandService destroyCommandService = new DestroyCommandService(
                terraformDestructionApplication.getCredentials(),
                properties);

        CommandExecutorOutputEntity destroyCommandOutput;

        try {
            destroyCommandOutput = commandExecutorService.executeCommand(destroyCommandService);
        } catch (CommandExecutorException e) {
            throw new TerraformException(e.getMessage());
        }

        String initCommandErrorOutput = destroyCommandOutput.getErrorOutput();

        if (Objects.nonNull(initCommandErrorOutput)){
            throw new TerraformException(initCommandErrorOutput);
        }
    }
}
