package com.resourcetracker.service.terraform.provider.aws;

import com.resourcetracker.entity.CommandExecutorOutputEntity;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.exception.CommandExecutorException;
import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.terraform.provider.aws.command.ApplyCommandService;
import com.resourcetracker.service.terraform.provider.aws.command.OutputCommandService;
import com.resourcetracker.service.terraform.provider.executor.CommandExecutorService;
import com.resourcetracker.service.terraform.provider.ITerraformProvider;
import com.resourcetracker.service.terraform.provider.aws.command.DestroyCommandService;
import com.resourcetracker.service.terraform.provider.aws.command.InitCommandService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import process.exceptions.SProcessNotYetStartedException;

import java.util.Objects;

/**
 * Represents Terraform provider implementation for AWS vendor.
 */
@ApplicationScoped
public class AWSTerraformProviderService implements ITerraformProvider {
    @Inject
    PropertiesEntity properties;

    @Inject
    CommandExecutorService commandExecutorService;

    /**
     * @see ITerraformProvider
     */
    public String apply(TerraformDeploymentApplication terraformDeploymentApplication) throws TerraformException {
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

        ApplyCommandService applyCommandService = new ApplyCommandService(
                terraformDeploymentApplication.getCredentials(),
                properties);

        CommandExecutorOutputEntity applyCommandOutput;

        try {
            applyCommandOutput = commandExecutorService.executeCommand(applyCommandService);
        } catch (CommandExecutorException e) {
            throw new TerraformException(e.getMessage());
        }

        String applyCommandErrorOutput = applyCommandOutput.getErrorOutput();

        if (Objects.nonNull(applyCommandErrorOutput)){
            throw new TerraformException(applyCommandErrorOutput);
        }

        OutputCommandService outputCommandService = new OutputCommandService(
                terraformDeploymentApplication.getCredentials(),
                properties);

        CommandExecutorOutputEntity outputCommandOutput;

        try {
            outputCommandOutput = commandExecutorService.executeCommand(outputCommandService);
        } catch (CommandExecutorException e) {
            throw new TerraformException(e.getMessage());
        }

        String outputCommandErrorOutput = outputCommandOutput.getErrorOutput();

        if (Objects.nonNull(outputCommandErrorOutput)){
            throw new TerraformException(outputCommandErrorOutput);
        }

        return outputCommandOutput.getNormalOutput();
    }

    /**
     * @see ITerraformProvider
     */
    public void destroy(TerraformDestructionApplication terraformDestructionApplication) throws TerraformException {
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
