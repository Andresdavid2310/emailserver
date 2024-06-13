package com.app.emailserver.domain.usecase.scheduler;

import com.app.emailserver.domain.model.data.email.EmailEntity;
import com.app.emailserver.domain.model.data.email.EmailStatus;
import com.app.emailserver.infrastructure.repository.EmailRepository;
import com.app.emailserver.infrastructure.repository.EmailStatusRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class EmailSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(EmailSchedulerService.class);

    @Value("${email.spam-status}")
    private String spamStatus;

    @Value("${email.target-email}")
    private String targetEmail;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailStatusRepository emailStatusRepository;

    @Transactional
    public void markEmailsAsSpam() {
        Optional<EmailStatus> optionalSpamStatusEntity = emailStatusRepository.findByName(spamStatus);
        if (optionalSpamStatusEntity.isEmpty()) {
            logger.warn("No se encontró el estado de spam con el nombre: {}", spamStatus);
            return;
        }

        List<EmailEntity> emails = emailRepository.findByEmailFrom(targetEmail);
        if (emails.isEmpty()) {
            logger.warn("No se encontraron correos electrónicos del remitente: {}", targetEmail);
            return;
        }

        EmailStatus spamStatusEntity = optionalSpamStatusEntity.get();

        for (EmailEntity email : emails) {
            email.setEmailStatus(spamStatusEntity);
            email.setUpdatedAt(LocalDateTime.now());
        }
        emailRepository.saveAll(emails);
    }
}
