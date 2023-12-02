package com.resourcetracker.entity;

import com.resourcetracker.dto.AWSSecretsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class AWSDeploymentCredentialsEntity {
    AWSSecretsDto file;

    String region;
}
