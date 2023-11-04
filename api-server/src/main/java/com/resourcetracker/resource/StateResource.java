package com.resourcetracker.resource;

import com.resourcetracker.api.StateResourceApi;
import com.resourcetracker.model.ResourceState;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class StateResource implements StateResourceApi {

    /**
     * @return
     */
    @Override
    public List<ResourceState> v1StateGet() {
        return null;
    }
}
