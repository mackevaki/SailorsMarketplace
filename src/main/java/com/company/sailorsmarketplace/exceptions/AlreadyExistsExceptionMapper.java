package com.company.sailorsmarketplace.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AlreadyExistsExceptionMapper implements ExceptionMapper<UserExistsException> {
    @Override
    public Response toResponse(UserExistsException ex) {
        return Response
            .status(Response.Status.CONFLICT.getStatusCode())
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(ex.getMessage())
            .build();
    }
}