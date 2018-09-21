package com.company.sailorsmarketplace.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AuthenticationExceptionHandler implements ExceptionMapper<AuthenticationException> {

    @Override
    public Response toResponse(AuthenticationException e) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
