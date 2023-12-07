package com.resourcetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Represents context sent to ResourceTracker Agent
 * as a variable during deployment operation.
 */
@Setter
@AllArgsConstructor(staticName = "of")
public class AgentContextEntity {
    /**
     * Represents ResourceTracker Agent requests to be executed.
     */
    @Setter
    @AllArgsConstructor(staticName = "of")
    static class Request {
        String name;

        String script;

        String frequency;
    }

    List<Request> requests;
}
