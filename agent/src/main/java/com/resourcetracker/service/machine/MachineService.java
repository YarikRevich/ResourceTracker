package com.resourcetracker.service.machine;

import com.resourcetracker.service.kafka.KafkaService;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Getter
@Service
public class MachineService {
    private static final Logger logger = LogManager.getLogger(MachineService.class);

    private final String hostName;

    private final String hostAddress;

    public MachineService() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            logger.fatal(e.getMessage());
        }

        this.hostName = localHost.getHostName();
        this.hostAddress = localHost.getHostAddress();
    }
}
