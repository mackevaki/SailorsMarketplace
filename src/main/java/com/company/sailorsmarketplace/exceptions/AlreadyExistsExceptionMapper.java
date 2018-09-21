package com.company.sailorsmarketplace.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AlreadyExistsExceptionMapper implements ExceptionMapper<UserExistsException> {
    public Response toResponse(UserExistsException ex) {
        return Response.status(Response.Status.CONFLICT.getStatusCode()).build();
    }
}