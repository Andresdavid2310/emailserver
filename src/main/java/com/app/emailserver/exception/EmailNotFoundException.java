package com.app.emailserver.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(Long emailId) {
        super(String.format("Email with ID: %d not found", emailId));
    }
}
