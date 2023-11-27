package com.resourcetracker.entity;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class AWSDeploymentCredentialsEntity {
    String file;

    String region;

    String profile;
}
