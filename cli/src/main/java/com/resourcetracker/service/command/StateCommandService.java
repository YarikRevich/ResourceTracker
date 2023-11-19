package com.resourcetracker.service.command;

import com.resourcetracker.api.TerraformResourceApi;
import com.resourcetracker.exception.StartCommandFailException;
import com.resourcetracker.service.config.ConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StateCommandService {
    private static final Logger logger = LogManager.getLogger(StateCommandService.class);

    private final TerraformResourceApi terraformResourceApi;

    private final ConfigService configService;

    public StateCommandService(@Autowired ConfigService configService) {
        this.configService = configService;

        ApiClient apiClient = new ApiClient()
                .setBasePath(configService.getConfig().getApiServer().getHost());

        this.terraformResourceApi = new TerraformResourceApi(apiClient);

    }

    public void process() {
        TerraformDeploymentApplication terraformDeploymentApplication = new TerraformDeploymentApplication();

        configService.getConfig().getRequests().forEach(element -> {
            String script = configService.getScript(element);

            //    terraformDeploymentApplication.addRequestsItem()
        });


        Mono<Void> response = terraformResourceApi.v1GetState(terraformDeploymentApplication)
                .doOnError(t -> logger.fatal(new StartCommandFailException().getMessage()));
        response.block();
    }
}
