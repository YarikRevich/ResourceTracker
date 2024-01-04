package com.resourcetracker.mapping;

import com.resourcetracker.exception.WorkspaceUnitInternalConfigFileNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class WorkspaceUnitInternalConfigFileNotFoundExceptionMapper
    implements ExceptionMapper<WorkspaceUnitInternalConfigFileNotFoundException> {
  @Override
  public Response toResponse(WorkspaceUnitInternalConfigFileNotFoundException e) {
    return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
        .entity(e.getMessage())
        .build();
  }
}
