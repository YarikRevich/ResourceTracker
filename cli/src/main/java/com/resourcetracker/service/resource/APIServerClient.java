package com.resourcetracker.service.resource;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class APIServerClient {
    final WebClient webClient;

    public APIServerClient() {
        webClient = WebClient.builder()
                .build();
    }

    public void postTerraformDeploy() {

    }

    public void postTerraformDestroy() {

    }

    public void getState() {

    }
}
