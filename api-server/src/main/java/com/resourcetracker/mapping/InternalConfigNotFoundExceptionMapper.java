package com.resourcetracker.mapping;

import com.resourcetracker.exception.InternalConfigNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InternalConfigNotFoundExceptionMapper
    implements ExceptionMapper<InternalConfigNotFoundException> {
  @Override
  public Response toResponse(InternalConfigNotFoundException e) {
    return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
        .entity(e.getMessage())
        .build();
  }
}
