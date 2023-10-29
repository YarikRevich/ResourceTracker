package com.resourcetracker.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class AWSProviderEntity {
    @Pattern(regexp = "^(((./)?)|((~/.)?)|((/?))?)([a-zA-Z/]*)((\\.([a-z]+))?)$")
    String credentials;

    @NotBlank
    String profile;

    @NotBlank
    String region;
}
