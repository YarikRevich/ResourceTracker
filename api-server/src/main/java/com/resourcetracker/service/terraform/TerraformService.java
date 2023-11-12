package com.resourcetracker.service.terraform;

//import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.terraform.command.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import process.SProcessExecutor;
import process.exceptions.NonMatchingOSException;
import process.exceptions.SProcessNotYetStartedException;

import java.io.IOException;
import java.util.Objects;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Import;
//import org.springframework.stereotype.Service;
//
//import com.resourcetracker.entity.ConfigEntity;

//import com.resourcetracker.services.api.TerraformAPI;
//import com.resourcetracker.services.provider.aws.AWS;
//import com.resourcetracker.services.provider.az.AZ;
//import com.resourcetracker.services.provider.common.IProvider;
//import com.resourcetracker.services.provider.gcp.GCP;

@ApplicationScoped
public class TerraformService {


  @Inject
  CommandExecutorService commandExecutorService;

  @Inject
  ApplyCommandService applyCommandService;

  @Inject
  DestroyCommandService destroyCommandService;

  @Inject
  InitCommandService initCommandService;

  @Inject
  OutputCommandService outputCommandService;

  /**
   * Starts remote execution on a chosen provider
   *
   * @return URL endpoint to the remote resources where execution is
   *         going
   */
  public void apply(TerraformDeploymentApplication terraformDeploymentApplication) throws TerraformException {
    try {
      processExecutor.executeCommand(initCommandService);
    } catch (IOException | NonMatchingOSException e) {
      throw new TerraformException(e.getMessage());
    }

    try {
      if (!initCommandService.waitForOutput()){
        throw new TerraformException();
      }
    } catch (IOException e){
      throw new TerraformException(e.getMessage());
    }

    String initCommandErrorOutput;

    try {
      initCommandErrorOutput = initCommandService.getErrorOutput();
    } catch (SProcessNotYetStartedException e) {
      throw new TerraformException(e.getMessage());
    }

    if (Objects.isNull(initCommandErrorOutput)){
      throw new TerraformException();
    }

    try {
      processExecutor.executeCommand(applyCommandService);
    } catch (IOException | NonMatchingOSException e) {
      throw new TerraformException(e.getMessage());
    }

    try {
      if (!applyCommandService.waitForOutput()){
        throw new TerraformException();
      }
    } catch (IOException e){
      throw new TerraformException(e.getMessage());
    }

    String applyCommandErrorOutput;

    try {
      applyCommandErrorOutput = applyCommandService.getErrorOutput();
    } catch (SProcessNotYetStartedException e) {
      throw new TerraformException(e.getMessage());
    }

    if (Objects.isNull(applyCommandErrorOutput)){
      throw new TerraformException();
    }
  }

  public void destroy(TerraformDestructionApplication terraformDestructionApplication) throws TerraformException {
    try {
      processExecutor.executeCommand(destroyCommandService);
    } catch (IOException | NonMatchingOSException e) {
      throw new TerraformException(e.getMessage());
    }

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
