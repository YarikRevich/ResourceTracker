package com.resourcetrackersdk.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;

@RestController
@EnableAutoConfiguration
public class API {
    @RequestMapping(path="/status", produces=MediaType.APPLICATION_JSON_VALUE)
    String status() {
        return "Hello World!";
    }
}