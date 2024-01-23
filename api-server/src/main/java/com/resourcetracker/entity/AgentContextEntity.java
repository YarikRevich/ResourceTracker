package com.resourcetracker.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/** Represents context sent to ResourceTracker Agent as a variable during deployment operation. */
@Getter
@AllArgsConstructor(staticName = "of")
public class AgentContextEntity {
  /** Represents ResourceTracker Agent requests to be executed. */
  @Getter
  @AllArgsConstructor(staticName = "of")
  public static class Request {
    private String name;

    private String script;

    private String frequency;
  }

  private List<Request> requests;
}
