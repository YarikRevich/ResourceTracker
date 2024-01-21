package com.resourcetracker.mapping;

import com.resourcetracker.exception.KafkaServiceNotAvailableException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/** Represents mapper for KafkaServiceNotAvailableException exception. */
@Provider
public class KafkaServiceNotAvailableExceptionMapper
    implements ExceptionMapper<KafkaServiceNotAvailableException> {
  @Override
  public Response toResponse(KafkaServiceNotAvailableException e) {
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
        .entity(e.getMessage())
        .build();
  }
}
