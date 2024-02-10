package com.paysharepal.paysharepal.infrastructure.exceptions;

public class UserNotExistsException extends Exception{
    public UserNotExistsException() {
        super();
    }

    public UserNotExistsException(String message) {
        super(message);
    }
}
