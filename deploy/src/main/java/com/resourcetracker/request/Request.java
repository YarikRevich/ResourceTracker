package com.resourcetracker.request;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;

public class Request {
    public static void isOk(String url) {
        RestTemplate restTemplate = new RestTemplate();        
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getStatusCode() == HttpStatus.OK;
    }
}
