package com.company.sailorsmarketplace.exceptions;

public class AuthenticationServiceException extends RuntimeException {
    public AuthenticationServiceException() {}

    public AuthenticationServiceException(String msg) {
        super(msg);
    }
}
