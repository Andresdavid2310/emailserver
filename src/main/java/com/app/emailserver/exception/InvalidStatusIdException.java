package com.app.emailserver.exception;

public class InvalidStatusIdException extends RuntimeException {
    public InvalidStatusIdException(Long statusId) {
        super(String.format("Invalid status ID: %d", statusId));
    }
}
