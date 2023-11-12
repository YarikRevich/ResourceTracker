package com.resourcetracker.service.terraform;

//import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.terraform.command.OutputCommand;
import com.resourcetracker.service.terraform.command.ApplyCommand;
import com.resourcetracker.service.terraform.command.DestroyCommand;
import com.resourcetracker.service.terraform.command.InitCommand;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
  private static final Logger logger = LogManager.getLogger(TerraformService.class);

  private final SProcessExecutor processExecutor;

  @Inject
  ApplyCommand applyCommand;

  @Inject
  DestroyCommand destroyCommand;

  @Inject
  InitCommand initCommand;

  @Inject
  OutputCommand outputCommand;

  TerraformService() {
    this.processExecutor = SProcessExecutor.getCommandExecutor();
  }

  /**
   * Starts remote execution on a chosen provider
   *
   * @return URL endpoint to the remote resources where execution is
   *         going
   */
  public void apply(TerraformDeploymentApplication terraformDeploymentApplication) throws TerraformException {
    try {
      processExecutor.executeCommand(initCommand);
    } catch (IOException | NonMatchingOSException e) {
      throw new TerraformException(e.getMessage());
    }

    try {
      if (!initCommand.waitForOutput()){
        throw new TerraformException();
      }
    } catch (IOException e){
      throw new TerraformException(e.getMessage());
    }

    String initCommandErrorOutput;

    try {
      initCommandErrorOutput = initCommand.getErrorOutput();
    } catch (SProcessNotYetStartedException e) {
      throw new TerraformException(e.getMessage());
    }

    if (Objects.isNull(initCommandErrorOutput)){
      throw new TerraformException();
    }

    try {
      processExecutor.executeCommand(applyCommand);
    } catch (IOException | NonMatchingOSException e) {
      throw new TerraformException(e.getMessage());
    }

    try {
      if (!applyCommand.waitForOutput()){
        throw new TerraformException();
      }
    } catch (IOException e){
      throw new TerraformException(e.getMessage());
    }

    String applyCommandErrorOutput;

    try {
      applyCommandErrorOutput = applyCommand.getErrorOutput();
    } catch (SProcessNotYetStartedException e) {
      throw new TerraformException(e.getMessage());
    }

    if (Objects.isNull(applyCommandErrorOutput)){
      throw new TerraformException();
    }
  }

  public void destroy(TerraformDestructionApplication terraformDestructionApplication) throws TerraformException {
    try {
      processExecutor.executeCommand(destroyCommand);
    } catch (IOException | NonMatchingOSException e) {
      throw new TerraformException(e.getMessage());
    }

    try {
      if (!destroyCommand.waitForOutput()){
        throw new TerraformException();
      }
    } catch (IOException e){
      throw new TerraformException(e.getMessage());
    }

    String destroyCommandErrorOutput;

    try {
      destroyCommandErrorOutput = destroyCommand.getErrorOutput();
    } catch (SProcessNotYetStartedException e) {
      throw new TerraformException(e.getMessage());
    }

    if (Objects.isNull(destroyCommandErrorOutput)){
      throw new TerraformException();
    }
  }
}
