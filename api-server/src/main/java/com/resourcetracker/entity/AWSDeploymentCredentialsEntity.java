package com.resourcetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class AWSDeploymentCredentialsEntity {
    AWSCredentialsEntity file;

    String region;

    String profile;
}
