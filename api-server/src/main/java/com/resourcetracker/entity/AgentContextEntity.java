package com.resourcetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor(staticName = "of")
public class AgentContextEntity {
    @Setter
    @AllArgsConstructor(staticName = "of")
    static class Request {
        String name;

        String script;

        String frequency;
    }

    List<Request> requests;
}
