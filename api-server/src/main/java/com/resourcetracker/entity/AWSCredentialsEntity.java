package com.resourcetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class AWSCredentialsEntity {
    String accessKey;

    String secretKey;
}
