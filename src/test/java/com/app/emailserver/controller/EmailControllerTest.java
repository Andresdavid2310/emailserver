package com.app.emailserver.controller;

import com.app.emailserver.application.service.EmailService;
import com.app.emailserver.domain.model.dto.input.EmailDto;
import com.app.emailserver.domain.model.dto.input.Emails;
import com.app.emailserver.domain.model.dto.output.EmailResponse;
import com.app.emailserver.infrastructure.controller.EmailController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmailControllerTest {

    @InjectMocks
    private EmailController emailController;

    @Mock
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllEmails() {
        Emails emails = new Emails();
        when(emailService.getAllEmails()).thenReturn(new ResponseEntity<>(emails, HttpStatus.OK));

        ResponseEntity<Emails> response = emailController.getAllEmails();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emails, response.getBody());
    }

    @Test
    public void testGetEmailById() {
        EmailDto emailDto = new EmailDto();
        when(emailService.getEmailById(anyLong())).thenReturn(new ResponseEntity<>(emailDto, HttpStatus.OK));

        ResponseEntity<EmailDto> response = emailController.getEmailById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emailDto, response.getBody());
    }

    @Test
    public void testUpdateEmail() {
        EmailResponse emailResponse = new EmailResponse(2, "");
        when(emailService.updateEmail(anyLong(), any(EmailDto.class))).thenReturn(new ResponseEntity<>(emailResponse, HttpStatus.OK));

        ResponseEntity<EmailResponse> response = emailController.updateEmail(1L, new EmailDto());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emailResponse, response.getBody());
    }

    @Test
    public void testCreateEmail() {
        Emails emails = new Emails();
        when(emailService.saveEmails(any(Emails.class))).thenReturn(new ResponseEntity<>(emails, HttpStatus.CREATED));

        ResponseEntity<Emails> response = emailController.createEmail(emails);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(emails, response.getBody());
    }

    @Test
    public void testDeleteEmail() {
        EmailResponse emailResponse = new EmailResponse(1,"");
        when(emailService.deleteEmail(anyLong())).thenReturn(new ResponseEntity<>(emailResponse, HttpStatus.OK));

        ResponseEntity<EmailResponse> response = emailController.deleteEmail(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emailResponse, response.getBody());
    }
}
