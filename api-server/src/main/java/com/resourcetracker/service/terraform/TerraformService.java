package com.resourcetracker.service.terraform;

//import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.entity.CommandExecutorOutputEntity;
import com.resourcetracker.exception.CommandExecutorException;
import com.resourcetracker.exception.TerraformException;
import com.resourcetracker.model.TerraformDeploymentApplication;
import com.resourcetracker.model.TerraformDestructionApplication;
import com.resourcetracker.service.executor.CommandExecutorService;
import com.resourcetracker.service.terraform.command.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
  }

  public void destroy(TerraformDestructionApplication terraformDestructionApplication) throws TerraformException {
//    try {
//      processExecutor.executeCommand(destroyCommandService);
//    } catch (IOException | NonMatchingOSException e) {
//      throw new TerraformException(e.getMessage());
//    }

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
