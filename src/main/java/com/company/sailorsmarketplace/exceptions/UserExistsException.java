package com.company.sailorsmarketplace.exceptions;

public class UserExistsException extends Exception {
    public UserExistsException(String email) {
        super("User with email" + email + " already exists!");
    }
}