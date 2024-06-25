package com.app.emailserver.exception;

import com.app.emailserver.infrastructure.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/response-status-exception")
    public void responseStatusException() {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
    }

    @GetMapping("/exception")
    public void exception() throws Exception {
        throw new Exception("Internal Server Error");
    }

    @GetMapping("/resource-not-found-exception")
    public void resourceNotFoundException() {
        throw new ResourceNotFoundException("Resource Not Found");
    }

    @GetMapping("/operation-not-allowed-exception")
    public void operationNotAllowedException() {
        throw new OperationNotAllowedException("Operation Not Allowed");
    }

    @GetMapping("/illegal-argument-exception")
    public void illegalArgumentException() {
        throw new IllegalArgumentException("Illegal Argument");
    }

    @GetMapping("/email-already-exists-exception")
    public void emailAlreadyExistsException() {
        throw new EmailAlreadyExistsException(1L);
    }

    @GetMapping("/invalid-status-id-exception")
    public void invalidStatusIdException() {
        throw new InvalidStatusIdException(5L);
    }

    @GetMapping("/email-not-found-exception")
    public void emailNotFoundException() {
        throw new EmailNotFoundException(5L);
    }
}
