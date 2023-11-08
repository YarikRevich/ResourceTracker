package com.resourcetracker.resource;

import com.resourcetracker.api.StateResourceApi;
import com.resourcetracker.model.ResourceState;
import com.resourcetracker.service.kafka.KafkaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class StateResource implements StateResourceApi {
    @Inject
    KafkaService kafkaService;

    /**
     * @return
     */
    @Override
    public List<ResourceState> v1StateGet() {
        return null;
    }
}
