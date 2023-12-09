package com.resourcetracker.entity;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Getter;

/** Represents context given for ResourceTracker Agent process. */
@Getter
public class ConfigEntity {
  @NotBlank List<Request> requests;

  /** Represents requests, which need to be executed */
  @Getter
  public static class Request {
    @NotBlank String script;

    @NotBlank String frequency;
  }
}
