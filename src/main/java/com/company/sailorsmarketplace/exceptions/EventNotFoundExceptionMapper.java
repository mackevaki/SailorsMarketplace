package com.company.sailorsmarketplace.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class EventNotFoundExceptionMapper implements ExceptionMapper<EventNotFoundException> {
    @Override
    public Response toResponse(EventNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
