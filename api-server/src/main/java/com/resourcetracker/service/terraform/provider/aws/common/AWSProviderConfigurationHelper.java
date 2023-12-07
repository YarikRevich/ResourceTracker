package com.resourcetracker.service.terraform.provider.aws.common;

//import com.resourcetracker.model.TerraformDeploymentApplicationCredentials;
import com.resourcetracker.service.terraform.common.TerraformConfigurationHelper;

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
    static public String getBackendConfig(Object credentials) {
//        return TerraformConfigurationHelper.getBackendConfig(new HashMap<>() {{
//            put("region", credentials.getRegion());
//            put("access_key", credentials.getSecrets().getAccessKey());
//            put("secret_key", credentials.getSecrets().getSecretKey());
//        }});
        return "";
    }

    /**
     * Composes environment variables for AWS vendor.
     * @param credentials AWS vendor credentials to perform operation.
     * @return composed environment variables.
     */
    static public String getEnvironmentVariables(Object credentials) {
//        return TerraformConfigurationHelper.getEnvironmentVariables(new HashMap<>() {{
//            put("AWS_ACCESS_KEY_ID", credentials.getSecrets().getAccessKey());
//            put("AWS_SECRET_ACCESS_KEY", credentials.getSecrets().getSecretKey());
//            put("AWS_REGION", credentials.getRegion());
//        }});
        return "";
    }

    /**
     * Composes input variables for AWS vendor.
     * @param context input for ResourceTracker Agent.
     * @param version version of ResourceTracker Agent implementation.
     * @return composed environment variables.
     */
    static public String getVariables(String context, String version) {
//        return TerraformConfigurationHelper.getVariables(new HashMap<>() {{
//            put("resourcetracker_agent_context", context);
//            put("resourcetracker_agent_version", version);
//        }});

        return "";
    }
}
