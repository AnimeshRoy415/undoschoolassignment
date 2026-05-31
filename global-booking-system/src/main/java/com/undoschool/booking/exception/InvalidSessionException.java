package com.undoschool.booking.exception;

public class InvalidSessionException
        extends RuntimeException {

    public InvalidSessionException(String message) {
        super(message);
    }
}