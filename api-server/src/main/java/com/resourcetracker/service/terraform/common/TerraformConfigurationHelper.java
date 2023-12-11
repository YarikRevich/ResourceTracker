package com.resourcetracker.service.terraform.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.resourcetracker.model.TerraformDeploymentApplication;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/** Contains helpful tools used for general Terraform configuration. */
public class TerraformConfigurationHelper {
  /**
   * Composes backend configuration.
   *
   * @param attributes attributes to be included.
   * @return composed backend configuration.
   */
  public static String getBackendConfig(Map<String, String> attributes) {
    return attributes.entrySet().stream()
        .map(
            element ->
                String.format("-backend-config='%s=%s'", element.getKey(), element.getValue()))
        .collect(Collectors.joining(" "));
  }

  /**
   * Composes environment variables.
   *
   * @param attributes attributes to be included.
   * @return composed environment variables.
   */
  public static String getEnvironmentVariables(Map<String, String> attributes) {
    return attributes.entrySet().stream()
        .map(element -> String.format("%s='%s'", element.getKey(), element.getValue()))
        .collect(Collectors.joining(" "));
  }

  /**
   * Composes input variables.
   *
   * @param attributes attributes to be included.
   * @return composed environment variables.
   */
  public static String getVariables(Map<String, String> attributes) {
    return attributes.entrySet().stream()
        .map(element -> String.format("-var %s='%s'", element.getKey(), element.getValue()))
        .collect(Collectors.joining(" "));
  }

  /**
   * Converts Terraform Deployment Application to ResourceTracker Agent context.
   *
   * @param terraformDeploymentApplication Terraform Deployment Application to be converted.
   * @return string representation of ResourceTracker Agent.
   */
  public static String getContext(TerraformDeploymentApplication terraformDeploymentApplication)
      throws IOException {
    ObjectMapper mapper =
        new ObjectMapper(new YAMLFactory())
            .configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    return mapper.writeValueAsString(terraformDeploymentApplication);
  }
}
