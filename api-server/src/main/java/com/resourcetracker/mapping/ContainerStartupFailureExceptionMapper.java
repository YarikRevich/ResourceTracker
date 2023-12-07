package com.resourcetracker.mapping;

import com.resourcetracker.exception.ContainerStartupFailureException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ContainerStartupFailureExceptionMapper implements ExceptionMapper<ContainerStartupFailureException> {
    @Override
    public Response toResponse(ContainerStartupFailureException e) {
        return Response
                .status(
                        Response.Status.BAD_REQUEST.getStatusCode())
                .entity(e.getMessage())
                .build();
    }
}
