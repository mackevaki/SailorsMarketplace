package com.company.sailorsmarketplace.exceptions;

import static java.lang.String.format;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException() {
        super();
    }

    public EventNotFoundException(final Long id) {
        super(format("Can't find event with id \"%d\"", id));
    }
}
