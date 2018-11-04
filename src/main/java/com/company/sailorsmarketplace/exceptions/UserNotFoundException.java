package com.company.sailorsmarketplace.exceptions;

import static java.lang.String.format;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(final String email) {
        super(format("Can't find user with email \"%s\"", email));    }

    public UserNotFoundException(final Long id) {
        super(format("Can't find user with id \"%d\"", id));
    }
}
