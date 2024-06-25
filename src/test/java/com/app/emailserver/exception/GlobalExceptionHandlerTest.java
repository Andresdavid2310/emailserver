package com.app.emailserver.exception;

import com.app.emailserver.infrastructure.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TestController.class, GlobalExceptionHandler.class})
@ContextConfiguration(classes = {GlobalExceptionHandler.class, TestController.class})
@Import(TestSecurityConfig.class)
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void handleResponseStatusException() throws Exception {
        mockMvc.perform(get("/test/response-status-exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Not Found"));
    }

    @Test
    void handleException() throws Exception {
        mockMvc.perform(get("/test/exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal Server Error"));
    }

    @Test
    void handleResourceNotFoundException() throws Exception {
        mockMvc.perform(get("/test/resource-not-found-exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":404,\"message\":\"Resource Not Found\"}"));
    }

    @Test
    void handleOperationNotAllowedException() throws Exception {
        mockMvc.perform(get("/test/operation-not-allowed-exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"Operation Not Allowed\"}"));
    }

    @Test
    void handleIllegalArgumentException() throws Exception {
        mockMvc.perform(get("/test/illegal-argument-exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"Illegal Argument\"}"));
    }

    @Test
    void handleEmailAlreadyExistsException() throws Exception {
        mockMvc.perform(get("/test/email-already-exists-exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"Email with ID: 1 already exists\"}"));
    }

    @Test
    void handleInvalidStatusIdException() throws Exception {
        mockMvc.perform(get("/test/invalid-status-id-exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"Invalid status ID: 5\"}"));
    }

    @Test
    void handleEmailNotFoundException() throws Exception {
        mockMvc.perform(get("/test/email-not-found-exception")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"status\":400,\"message\":\"Email with ID: 5 not found\"}"));
    }
}
