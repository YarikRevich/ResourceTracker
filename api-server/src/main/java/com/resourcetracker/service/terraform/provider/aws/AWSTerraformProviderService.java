package com.resourcetracker.service.terraform.provider.aws;

import com.resourcetracker.converter.AgentContextToJsonConverter;
import com.resourcetracker.converter.DeploymentRequestsToAgentContextConverter;
import com.resourcetracker.dto.CommandExecutorOutputDto;
import com.resourcetracker.entity.PropertiesEntity;
import com.resourcetracker.entity.VariableFileEntity;
import com.resourcetracker.exception.CommandExecutorException;
import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.exception.WorkspaceUnitDirectoryNotFoundException;
import com.resourcetracker.exception.WorkspaceUnitVariableFileNotFoundException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.terraform.provider.ITerraformProvider;
import com.resourcetracker.service.terraform.provider.aws.command.DestroyCommandService;
import com.resourcetracker.service.terraform.provider.aws.command.InitCommandService;
import com.resourcetracker.service.terraform.provider.executor.CommandExecutorService;
import com.resourcetracker.service.terraform.workspace.WorkspaceService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

/** Represents Terraform provider implementation for AWS vendor. */
@ApplicationScoped
public class AWSTerraformProviderService implements ITerraformProvider {
  @Inject PropertiesEntity properties;

  @Inject WorkspaceService workspaceService;

  @Inject CommandExecutorService commandExecutorService;

  /**
   * @see ITerraformProvider
   */
  public String apply(TerraformDeploymentApplication terraformDeploymentApplication)
      throws TerraformException {
    String workspaceUnitKey =
        workspaceService.createUnitKey(
            terraformDeploymentApplication.getCredentials().getSecrets().getAccessKey(),
            terraformDeploymentApplication.getCredentials().getSecrets().getSecretKey());

    try {
      workspaceService.createUnitDirectory(workspaceUnitKey);
    } catch (IOException e) {
      throw new TerraformException(e.getMessage());
    }

    String workspaceUnitDirectory;

    try {
      workspaceUnitDirectory = workspaceService.getUnitDirectory(workspaceUnitKey);
    } catch (WorkspaceUnitDirectoryNotFoundException e) {
      throw new TerraformException(e.getMessage());
    }

    InitCommandService initCommandService =
        new InitCommandService(
            workspaceUnitDirectory,
            terraformDeploymentApplication.getCredentials(),
            properties.getTerraformDirectory());

    CommandExecutorOutputDto initCommandOutput;

    try {
      initCommandOutput = commandExecutorService.executeCommand(initCommandService);
    } catch (CommandExecutorException e) {
      throw new TerraformException(e.getMessage());
    }

    String initCommandErrorOutput = initCommandOutput.getErrorOutput();

    if (Objects.nonNull(initCommandErrorOutput) && !initCommandErrorOutput.isEmpty()) {
      throw new TerraformException(initCommandErrorOutput);
    }

    String agentContext =
        AgentContextToJsonConverter.convert(
            DeploymentRequestsToAgentContextConverter.convert(
                terraformDeploymentApplication.getRequests()));

    //    ApplyCommandService applyCommandService =
    //        new ApplyCommandService(
    //            agentContext,
    //            workspaceUnitDirectory,
    //            terraformDeploymentApplication.getCredentials(),
    //            properties.getTerraformDirectory(),
    //            properties.getGitCommitId());
    //
    //    CommandExecutorOutputDto applyCommandOutput;
    //
    //    try {
    //      applyCommandOutput = commandExecutorService.executeCommand(applyCommandService);
    //    } catch (CommandExecutorException e) {
    //      throw new TerraformException(e.getMessage());
    //    }
    //
    //    String applyCommandErrorOutput = applyCommandOutput.getErrorOutput();
    //
    //    if (Objects.nonNull(applyCommandErrorOutput)) {
    //      throw new TerraformException(applyCommandErrorOutput);
    //    }

    try {
      workspaceService.createVariableFile(
          workspaceUnitDirectory, VariableFileEntity.of(agentContext, properties.getGitCommitId()));
    } catch (IOException e) {
      throw new TerraformException(e.getMessage());
    }
    //
    //    OutputCommandService outputCommandService =
    //        new OutputCommandService(terraformDeploymentApplication.getCredentials(), properties);
    //
    //    CommandExecutorOutputDto outputCommandOutput;
    //
    //    try {
    //      outputCommandOutput = commandExecutorService.executeCommand(outputCommandService);
    //    } catch (CommandExecutorException e) {
    //      throw new TerraformException(e.getMessage());
    //    }
    //
    //    String outputCommandErrorOutput = outputCommandOutput.getErrorOutput();
    //
    //    if (Objects.nonNull(outputCommandErrorOutput)) {
    //      throw new TerraformException(outputCommandErrorOutput);
    //    }

    //    return outputCommandOutput.getNormalOutput();
    return "";
  }

  /**
   * @see ITerraformProvider
   */
  public void destroy(TerraformDestructionApplication terraformDestructionApplication)
      throws TerraformException {
    String workspaceUnitKey =
        workspaceService.createUnitKey(
            terraformDestructionApplication.getCredentials().getSecrets().getAccessKey(),
            terraformDestructionApplication.getCredentials().getSecrets().getSecretKey());

    if (!workspaceService.isUnitDirectoryExist(workspaceUnitKey)) {
      throw new TerraformException(new WorkspaceUnitDirectoryNotFoundException().getMessage());
    }

    String workspaceUnitDirectory;

    try {
      workspaceUnitDirectory = workspaceService.getUnitDirectory(workspaceUnitKey);
    } catch (WorkspaceUnitDirectoryNotFoundException e) {
      throw new TerraformException(e.getMessage());
    }

    if (!workspaceService.isVariableFileExist(workspaceUnitDirectory)) {
      throw new TerraformException(new WorkspaceUnitVariableFileNotFoundException().getMessage());
    }

    VariableFileEntity variableFile;
    try {
      variableFile = workspaceService.getVariableFileContent(workspaceUnitDirectory);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    DestroyCommandService destroyCommandService =
        new DestroyCommandService(
            variableFile.getAgentContext(),
            workspaceUnitDirectory,
            terraformDestructionApplication.getCredentials(),
            properties.getTerraformDirectory(),
            variableFile.getAgentVersion());

    CommandExecutorOutputDto destroyCommandOutput;

    try {
      destroyCommandOutput = commandExecutorService.executeCommand(destroyCommandService);
    } catch (CommandExecutorException e) {
      throw new TerraformException(e.getMessage());
    }

    String destroyCommandErrorOutput = destroyCommandOutput.getErrorOutput();

    if (Objects.nonNull(destroyCommandErrorOutput) && !destroyCommandErrorOutput.isEmpty()) {
      throw new TerraformException(destroyCommandErrorOutput);
    }

    try {
      workspaceService.removeUnitDirectory(workspaceUnitKey);
    } catch (IOException e) {
      throw new TerraformException(e.getMessage());
    }
  }
}
