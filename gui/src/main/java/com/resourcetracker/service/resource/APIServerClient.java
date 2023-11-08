package com.resourcetracker.service.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class APIServerClient {
    final WebClient webClient;

    @Autowired
    ConfigService configService;

    public APIServerClient() {
        webClient = WebClient.builder()
                .build();
    }

    public void v1TerraformApplyPost() {

    }

    public void v1TerraformDestroyPost() {

    }

    public void v1StateGet() {

    }

    public void v1ReadinessProbeGet() {

    }

    public void v1LivenessProbeGet() {

    }
}
