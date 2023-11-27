package com.resourcetracker.service.terraform.provider.aws;

import com.resourcetracker.entity.AWSDeploymentCredentialsEntity;
import com.resourcetracker.entity.CommandExecutorOutputEntity;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.CommandExecutorException;
import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDeploymentApplicationCredentials;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.executor.CommandExecutorService;
import com.resourcetracker.service.terraform.provider.IProvider;
import com.resourcetracker.service.terraform.provider.aws.command.DestroyCommandService;
import com.resourcetracker.service.terraform.provider.aws.command.InitCommandService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import process.exceptions.SProcessNotYetStartedException;

import java.io.IOException;
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

        InitCommandService initCommandService = new InitCommandService(
                AWSDeploymentCredentialsEntity.of(
                        credentials.getFile(),
                        credentials.getRegion(),
                        credentials.getProfile()
                ),
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



        return null;
    }

    /**
     * @see IProvider
     */
    public void destroy(TerraformDestructionApplication terraformDestructionApplication) throws TerraformException {
//    try {
//      processExecutor.executeCommand(destroyCommandService);
//    } catch (IOException | NonMatchingOSException e) {
//      throw new TerraformException(e.getMessage());
//    }

        DestroyCommandService destroyCommandService = new DestroyCommandService(
                properties
        );

        try {
            if (!destroyCommandService.waitForOutput()){
                throw new TerraformException();
            }
        } catch (IOException e){
            throw new TerraformException(e.getMessage());
        }

        String destroyCommandErrorOutput;

        try {
            destroyCommandErrorOutput = destroyCommandService.getErrorOutput();
        } catch (SProcessNotYetStartedException e) {
            throw new TerraformException(e.getMessage());
        }

        if (Objects.isNull(destroyCommandErrorOutput)){
            throw new TerraformException();
        }
    }
}
