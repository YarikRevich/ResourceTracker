package com.resourcetracker.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.resourcetracker.model.ReadinessCheckResult;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.microprofile.health.HealthCheckResponse;

public class HealthCheckResponseToReadinessCheckResult {
  private static final Logger logger =
      LogManager.getLogger(HealthCheckResponseToReadinessCheckResult.class);

  /**
   * Converts given health check response to readiness check result entity.
   *
   * @param content given content to be converted.
   * @return converted readiness check result entity.
   */
  public static ReadinessCheckResult convert(HealthCheckResponse content) {
    ObjectMapper mapper = new ObjectMapper();

    String contentRaw = null;
    try {
      contentRaw = mapper.writeValueAsString(content);
    } catch (IOException e) {
      logger.fatal(e.getMessage());
    }

    ObjectReader reader = mapper.reader().forType(new TypeReference<ReadinessCheckResult>() {});
    try {
      return reader.<ReadinessCheckResult>readValues(contentRaw).readAll().getFirst();
    } catch (IOException e) {
      logger.fatal(e.getMessage());
    }

    return null;
  }
}
