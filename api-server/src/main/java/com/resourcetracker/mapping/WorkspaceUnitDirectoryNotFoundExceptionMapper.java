package com.resourcetracker.mapping;

import com.resourcetracker.exception.WorkspaceUnitDirectoryNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class WorkspaceUnitDirectoryNotFoundExceptionMapper
    implements ExceptionMapper<WorkspaceUnitDirectoryNotFoundException> {
  @Override
  public Response toResponse(WorkspaceUnitDirectoryNotFoundException e) {
    return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
        .entity(e.getMessage())
        .build();
  }
}
