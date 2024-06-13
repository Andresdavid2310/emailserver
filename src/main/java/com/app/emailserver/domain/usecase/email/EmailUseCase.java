package com.app.emailserver.domain.usecase.email;

import com.app.emailserver.domain.model.data.email.EmailEntity;
import com.app.emailserver.domain.model.data.email.EmailStatus;
import com.app.emailserver.infrastructure.exception.EmailAlreadyExistsException;
import com.app.emailserver.infrastructure.exception.EmailNotFoundException;
import com.app.emailserver.infrastructure.exception.InvalidStatusIdException;
import com.app.emailserver.infrastructure.repository.EmailRepository;
import com.app.emailserver.infrastructure.repository.EmailStatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailUseCase {

    private final EmailRepository emailRepository;
    private final EmailStatusRepository emailStatusRepository;

    public EmailUseCase(EmailRepository emailRepository, EmailStatusRepository emailStatusRepository) {
        this.emailRepository = emailRepository;
        this.emailStatusRepository = emailStatusRepository;
    }


    public List<EmailEntity> getALlEmails() {
        return emailRepository.findAll();
    }

    public EmailEntity getEmailById(Long id){
        return emailRepository.findById(id).orElseThrow(() -> new EmailNotFoundException(id));
    }

    @Transactional
    public void saveAllEmails(List<EmailEntity> emails) {
        for (EmailEntity email : emails) {
            EmailStatus status = emailStatusRepository.findById(email.getEmailStatus().getId())
                    .orElseThrow(() -> new InvalidStatusIdException(email.getEmailStatus().getId()));
            email.setEmailStatus(status);

            if (email.getEmailTo() != null) {
                email.getEmailTo().forEach(address -> address.setEmailEntity(email));
            }

            if (email.getEmailCC() != null) {
                email.getEmailCC().forEach(addressCC -> addressCC.setEmailEntity(email));
            }

            if (emailRepository.existsById(email.getEmailId())){
                throw new EmailAlreadyExistsException(email.getEmailId());
            }else {
                emailRepository.save(email);
            }
        }
    }

    @Transactional
    public void deleteEmail(Long id) {
        EmailEntity email = emailRepository.findById(id).orElseThrow(()-> new EmailNotFoundException(id));
        emailRepository.delete(email);
    }

    @Transactional
    public  void updateMail(Long id, EmailEntity email) {
        EmailEntity existingEmail = emailRepository.findById(id)
                .orElseThrow(() -> new EmailNotFoundException(id));

        if (!"BORRADOR".equals(existingEmail.getEmailStatus().getName())) {
            throw new InvalidStatusIdException(id);
        }

        existingEmail.setEmailFrom(email.getEmailFrom());
        existingEmail.setEmailBody(email.getEmailBody());
        existingEmail.setEmailStatus(emailStatusRepository.findById(email.getEmailStatus().getId())
                .orElseThrow(() -> new InvalidStatusIdException(id)));
        existingEmail.setUpdatedAt(LocalDateTime.now());

        if (email.getEmailTo() != null) {
            existingEmail.getEmailTo().clear();
            email.getEmailTo().forEach(address -> {
                address.setEmailEntity(existingEmail);
                existingEmail.getEmailTo().add(address);
            });
        }

        if (email.getEmailCC() != null) {
            existingEmail.getEmailCC().clear();
            email.getEmailCC().forEach(addressCC -> {
                addressCC.setEmailEntity(existingEmail);
                existingEmail.getEmailCC().add(addressCC);
            });
        }
        emailRepository.save(existingEmail);

    }
}
