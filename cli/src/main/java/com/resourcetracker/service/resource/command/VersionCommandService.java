package com.resourcetracker.service.resource.command;

import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Represents
 */
@Service
public class VersionCommandService {
    private static final Logger logger = LogManager.getLogger(VersionCommandService.class);

    private final InfoResourceApi infoResourceApi;

    private final ConfigService configService;

    public StopCommandService(@Autowired ConfigService configService) {
        this.configService = configService;

        ApiClient apiClient = new ApiClient()
                .setBasePath(configService.getConfig().getApiServer().getHost());

        this.infoResourceApi = new InfoResourceApi(apiClient);
    }

    public void process() {

    }
}
