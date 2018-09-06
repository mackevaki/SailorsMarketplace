package com.company.sailorsmarketplace.exceptions;

public class UserExistsException extends Exception {
    public UserExistsException(String username) {
        super("User " + username + " already exists!");
    }
}