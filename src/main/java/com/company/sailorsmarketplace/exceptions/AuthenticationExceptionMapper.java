package com.company.sailorsmarketplace.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

    @Override
    public Response toResponse(AuthenticationException e) {
        return Response
            .status(Response.Status.UNAUTHORIZED.getStatusCode())
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(e.getMessage())
            .build();
    }
}
