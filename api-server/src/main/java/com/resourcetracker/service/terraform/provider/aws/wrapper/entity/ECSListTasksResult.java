package com.resourcetracker.service.terraform.provider.aws.wrapper.entity;

public class ECSListTasksResult{
  private String taskArn;

  public String getTaskArn() {
    return this.taskArn;
  }

  public void setTaskArn(String taskArn) {
    this.taskArn = taskArn;
  }
}
