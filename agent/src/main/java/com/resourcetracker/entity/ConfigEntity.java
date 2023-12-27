package com.resourcetracker.entity;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Getter;

/** Represents context given for ResourceTracker Agent process. */
@Getter
public class ConfigEntity {
  @NotBlank private List<Request> requests;

  /** Represents requests, which need to be executed */
  @Getter
  public static class Request {
    @NotBlank private String name;

    @NotBlank private String script;

    @NotBlank private String frequency;
  }
}
