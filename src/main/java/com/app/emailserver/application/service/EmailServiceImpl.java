package com.app.emailserver.application.service;

import com.app.emailserver.domain.model.mapper.EmailMapper;
import com.app.emailserver.domain.model.dto.input.EmailDto;
import com.app.emailserver.domain.model.dto.input.Emails;
import com.app.emailserver.domain.model.dto.output.EmailResponse;
import com.app.emailserver.domain.usecase.email.EmailUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailUseCase emailUseCase;
    private final EmailMapper emailMapper;

    public EmailServiceImpl(EmailUseCase emailUseCase, EmailMapper emailMapper) {
        this.emailUseCase = emailUseCase;
        this.emailMapper = emailMapper;
    }

    @Override
    public ResponseEntity<Emails> getAllEmails() {
        return ResponseEntity.ok(emailMapper.toEmailsResponse(emailUseCase.getALlEmails()));
    }

    public ResponseEntity<EmailDto> getEmailById(Long id) {
        return ResponseEntity.ok(emailMapper.toEmailDto(emailUseCase.getEmailById(id)));
    }
    public ResponseEntity<Emails> saveEmails(Emails emails) {
        emailUseCase.saveAllEmails(emailMapper.toEmailEntities(emails.getEmails()));
        return ResponseEntity.created(URI.create("/emails/")).body(emails);
    }

    public ResponseEntity<EmailResponse> updateEmail(Long id, EmailDto email) {
        emailUseCase.updateMail(id, emailMapper.toEmailEntity(email));
        return ResponseEntity.ok(new EmailResponse(200,"The Email was Updated"));
    }

    public ResponseEntity<EmailResponse> deleteEmail(Long id) {
        emailUseCase.deleteEmail(id);
        return ResponseEntity.ok(new EmailResponse(200, "The Email was Deleted"));
    }
}
