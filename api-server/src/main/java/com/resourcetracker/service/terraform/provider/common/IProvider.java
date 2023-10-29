package com.resourcetracker.service.terraform.provider.common;

//import com.resourcetracker.services.api.TerraformAPI;

public interface IProvider {
  /**
   * Starts remote execution on a chosen provider
   * @return address for Kafka instance
   */
  public String start();

  /**
   * Stops remote execution on a chosen provider
   */
  public void stop();
}
