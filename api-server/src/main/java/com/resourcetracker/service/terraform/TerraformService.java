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

      }
    } catch (IOException e){
      logger.fatal(e);
    }

    try {
      System.out.println(initCommand.getErrorOutput());
    } catch (SProcessNotYetStartedException e) {
      logger.fatal(e);
    }

    try {
      System.out.println(initCommand.getNormalOutput());
    } catch (SProcessNotYetStartedException e) {
      throw new RuntimeException(e);
    }






    try {
      processExecutor.executeCommand(applyCommand);
    } catch (IOException | NonMatchingOSException e) {
      logger.fatal(e);
    }

    try {
      if (!applyCommand.waitForOutput()){

      }
    } catch (IOException e){
      logger.fatal(e);
    }

    try {
      System.out.println(applyCommand.getErrorOutput());
    } catch (SProcessNotYetStartedException e) {
      logger.fatal(e);
    }

    try {
      System.out.println(applyCommand.getNormalOutput());
    } catch (SProcessNotYetStartedException e) {
      throw new RuntimeException(e);
    }
  }

  public void destroy(TerraformDestructionApplication terraformDestructionApplication) throws TerraformException {

  }
}
