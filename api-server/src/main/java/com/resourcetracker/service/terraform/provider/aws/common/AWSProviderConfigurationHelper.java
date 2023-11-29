package com.resourcetracker.service.terraform.provider.aws.common;

import com.resourcetracker.entity.AWSDeploymentCredentialsEntity;
import com.resourcetracker.model.TerraformDeploymentApplicationCredentials;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Contains helpful tools used for Terraform AWS provider configuration.
 */
public class AWSProviderConfigurationHelper {
    /**
     * Composes backend configuration for S3 initialization.
     * @param credentials AWS vendor credentials to perform operation.
     * @return composed backend configuration.
     */
    static public String getBackendConfig(TerraformDeploymentApplicationCredentials credentials) {
        return new HashMap<String, String>() {{
            put("region", credentials.getRegion());
            put("access_key", credentials.getAccessKey());
            put("secret_key", credentials.getSecretKey());
        }}
                .entrySet()
                .stream()
                .map(element -> String.format(
                        "-backend-config='%s=%s'",
                        element.getKey(),
                        element.getValue()))
                .collect(Collectors.joining(" "));
    }

    /**
     * Composes environment variables for AWS vendor.
     * @param credentials AWS vendor credentials to perform operation.
     * @return composed environment variables.
     */
    static public String getEnvironmentVariables(TerraformDeploymentApplicationCredentials credentials) {
        return new HashMap<String, String>() {{
            put("AWS_ACCESS_KEY_ID", credentials.getAccessKey());
            put("AWS_SECRET_ACCESS_KEY", credentials.getSecretKey());
            put("AWS_PROFILE", credentials.getProfile());
            put("AWS_REGION", credentials.getRegion());
        }}
                .entrySet()
                .stream()
                .map(element -> String.format(
                        "%s=%s",
                        element.getKey(),
                        element.getValue()))
                .collect(Collectors.joining(" "));
    }
}
