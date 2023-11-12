package com.resourcetracker.service.vendor.aws.wrapper.entity;

import lombok.Data;

@Data
public class ECSDescribeNetworkInterfacesResult {
  private String containerPublicIP;
}
