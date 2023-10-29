package com.resourcetracker.service.terraform.provider.aws.wrapper.entity;

public class ECSDescribeTaskResult {
  private String networkInterfaceID;

  public String getNetworkInterfaceID() {
    return this.networkInterfaceID;
  }

  public void setNetworkInterfaceID(String networkInterfaceID) {
    this.networkInterfaceID = networkInterfaceID;
  }
}
