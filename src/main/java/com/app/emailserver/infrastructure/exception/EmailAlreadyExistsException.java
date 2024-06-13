package com.app.emailserver.infrastructure.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(Long emailId) {
        super(String.format("Email with ID: %d already exists", emailId));
    }
}
