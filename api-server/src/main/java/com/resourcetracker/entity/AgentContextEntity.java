package com.resourcetracker.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Setter;

/** Represents context sent to ResourceTracker Agent as a variable during deployment operation. */
@Setter
@AllArgsConstructor(staticName = "of")
public class AgentContextEntity {
  /** Represents ResourceTracker Agent requests to be executed. */
  @Setter
  @AllArgsConstructor(staticName = "of")
  static class Request {
    String name;

    String script;

    String frequency;
  }

  List<Request> requests;
}
