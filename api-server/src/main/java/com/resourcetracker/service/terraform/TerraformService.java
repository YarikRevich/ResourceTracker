package com.resourcetracker.service.terraform;

//import com.resourcetracker.service.config.ConfigService;
import com.resourcetracker.service.terraform.command.OutputCommand;
import com.resourcetracker.service.terraform.command.ApplyCommand;
import com.resourcetracker.service.terraform.command.DestroyCommand;
import com.resourcetracker.service.terraform.command.InitCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

//@Service
public class TerraformService {
  private static final Logger logger = LogManager.getLogger(TerraformService.class);

//  @Autowired
//  private ConfigService configService;
//
//  @Autowired
//  private ApplyCommand applyCommand;
//
//  @Autowired
//  private DestroyCommand destroyCommand;
//
//  @Autowired
//  private InitCommand initCommand;
//
//  @Autowired
//  private OutputCommand outputCommand;

  /**
   * Starts remote execution on a chosen provider
   *
   * @return URL endpoint to the remote resources where execution is
   *         going
   */
  public String apply() {
//    configService.getConfig().getCloud().getProvider().toString();

    return "";
//		return chosenProvider.start();
  }

  public void destroy() {

  }
}
