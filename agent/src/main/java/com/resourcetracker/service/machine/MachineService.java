package com.resourcetracker.service.machine;

import java.net.InetAddress;
import java.net.UnknownHostException;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/** MachineService provides access to current machine info. */
@Getter
@Service
public class MachineService {
  private static final Logger logger = LogManager.getLogger(MachineService.class);

  private final String hostName;

  private final String hostAddress;

  public MachineService() throws UnknownHostException {
    InetAddress localHost = InetAddress.getLocalHost();

    this.hostName = localHost.getHostName();
    this.hostAddress = localHost.getHostAddress();
  }
}
