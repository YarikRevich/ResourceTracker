package com.resourcetracker.mapping;

import com.resourcetracker.exception.TerraformException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/** Represents mapper for TerraformException exception. */
@Provider
public class TerraformExceptionMapper implements ExceptionMapper<TerraformException> {
  @Override
  public Response toResponse(TerraformException e) {
    return Response.status(Response.Status.BAD_REQUEST.getStatusCode())
        .entity(e.getMessage())
        .build();
  }
}
