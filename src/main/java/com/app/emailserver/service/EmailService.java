package com.app.emailserver.service;

import com.app.emailserver.domain.model.dto.input.EmailDto;
import com.app.emailserver.domain.model.dto.input.Emails;
import com.app.emailserver.domain.model.dto.output.EmailResponse;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    ResponseEntity<Emails> getAllEmails();
    ResponseEntity<EmailDto> getEmailById(Long id);
    ResponseEntity<Emails> saveEmails(Emails emails);
    ResponseEntity<EmailResponse> updateEmail(Long id, EmailDto emailDto);
    ResponseEntity<EmailResponse> deleteEmail(Long id);
}
