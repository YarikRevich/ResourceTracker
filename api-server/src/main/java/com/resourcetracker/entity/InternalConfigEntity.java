package com.resourcetracker.entity;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/** Represents internal configuration model used for ResourceTracker API Server operations. */
@Getter
@ApplicationScoped
public class InternalConfigEntity {
  /** Represents Kafka related properties. */
  @Getter
  public static class Kafka {
    @NotBlank public String host;
  }

  @Valid public Kafka kafka;
}
