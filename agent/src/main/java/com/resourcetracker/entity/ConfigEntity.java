package com.resourcetracker.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.Getter;

/** Represents context given for ResourceTracker Agent process. */
@Getter
public class ConfigEntity {
  /** Represents requests, which need to be executed */
  @Getter
  public static class Request {
    @NotBlank public String name;

    @NotBlank public String script;

    @NotBlank public String frequency;
  }

  @Valid @NotBlank public List<Request> requests;
}
