package com.resourcetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** Represents readiness check command result. */
@Getter
@AllArgsConstructor(staticName = "of")
public class HealthCheckInternalCommandResultDto {
  private Boolean status;

  private String error;
}
