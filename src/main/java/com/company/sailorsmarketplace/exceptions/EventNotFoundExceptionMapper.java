package com.company.sailorsmarketplace.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EventNotFoundExceptionMapper implements ExceptionMapper<EventNotFoundException> {
    @Override
    public Response toResponse(EventNotFoundException exception) {
        return Response
            .status(Response.Status.NOT_FOUND.getStatusCode())
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(exception.getMessage())
            .build();
    }
}
