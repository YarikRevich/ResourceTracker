package com.resourcetracker.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AWSProviderEntity {
    @Pattern(regexp = "^(((./)?)|((~/.)?)|((/?))?)([a-zA-Z/]*)((\\.([a-z]+))?)$")
    String credentials;

    @NotBlank
    String profile;

    @NotBlank
    String region;
}
