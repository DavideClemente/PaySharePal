package com.paysharepal.paysharepal.infrastructure.exceptions;

public class GroupNotExistsException extends Exception{
    public GroupNotExistsException() {
        super();
    }

    public GroupNotExistsException(String message) {
        super(message);
    }
}
