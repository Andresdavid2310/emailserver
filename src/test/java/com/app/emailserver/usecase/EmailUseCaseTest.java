package com.app.emailserver.usecase;

import com.app.emailserver.domain.model.data.email.EmailEntity;
import com.app.emailserver.domain.model.data.email.EmailStatus;
import com.app.emailserver.domain.usecase.email.EmailUseCase;
import com.app.emailserver.infrastructure.exception.EmailAlreadyExistsException;
import com.app.emailserver.infrastructure.exception.EmailNotFoundException;
import com.app.emailserver.infrastructure.exception.InvalidStatusIdException;
import com.app.emailserver.infrastructure.repository.EmailRepository;
import com.app.emailserver.infrastructure.repository.EmailStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailUseCaseTest {

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private EmailStatusRepository emailStatusRepository;

    @InjectMocks
    private EmailUseCase emailUseCase;

    private EmailEntity emailEntity;
    private EmailStatus emailStatus;

    @BeforeEach
    public void setUp() {
        emailStatus = new EmailStatus();
        emailStatus.setId(1L);
        emailStatus.setName("BORRADOR");

        emailEntity = new EmailEntity();
        emailEntity.setEmailId(1L);
        emailEntity.setEmailFrom("correo@correo.com");
        emailEntity.setEmailBody("Test body");
        emailEntity.setEmailStatus(emailStatus);
    }

    @Test
    public void testGetAllEmails() {
        when(emailRepository.findAll()).thenReturn(Collections.singletonList(emailEntity));

        List<EmailEntity> emails = emailUseCase.getALlEmails();

        assertNotNull(emails);
        assertEquals(1, emails.size());
        verify(emailRepository, times(1)).findAll();
    }

    @Test
    public void testGetEmailById() {
        when(emailRepository.findById(1L)).thenReturn(Optional.of(emailEntity));

        EmailEntity foundEmail = emailUseCase.getEmailById(1L);

        assertNotNull(foundEmail);
        assertEquals(emailEntity.getEmailId(), foundEmail.getEmailId());
        verify(emailRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetEmailById_NotFound() {
        when(emailRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class, () -> emailUseCase.getEmailById(1L));
        verify(emailRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveAllEmails() {
        when(emailStatusRepository.findById(1L)).thenReturn(Optional.of(emailStatus));
        when(emailRepository.existsById(1L)).thenReturn(false);

        emailUseCase.saveAllEmails(Collections.singletonList(emailEntity));

        verify(emailRepository, times(1)).save(emailEntity);
    }

    @Test
    public void testSaveAllEmails_EmailAlreadyExists() {
        when(emailStatusRepository.findById(1L)).thenReturn(Optional.of(emailStatus));
        when(emailRepository.existsById(1L)).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> emailUseCase.saveAllEmails(Collections.singletonList(emailEntity)));
        verify(emailRepository, never()).save(any(EmailEntity.class));
    }

    @Test
    public void testDeleteEmail() {
        when(emailRepository.findById(1L)).thenReturn(Optional.of(emailEntity));

        emailUseCase.deleteEmail(1L);

        verify(emailRepository, times(1)).delete(emailEntity);
    }

    @Test
    public void testDeleteEmail_NotFound() {
        when(emailRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class, () -> emailUseCase.deleteEmail(1L));
        verify(emailRepository, never()).delete(any(EmailEntity.class));
    }

    @Test
    public void testUpdateMail() {
        EmailEntity updatedEmail = new EmailEntity();
        updatedEmail.setEmailFrom("correo@correo.com");
        updatedEmail.setEmailBody("Updated body");
        updatedEmail.setEmailStatus(emailStatus);

        when(emailRepository.findById(1L)).thenReturn(Optional.of(emailEntity));
        when(emailStatusRepository.findById(1L)).thenReturn(Optional.of(emailStatus));

        emailUseCase.updateMail(1L, updatedEmail);

        verify(emailRepository, times(1)).save(emailEntity);
        assertEquals("correo@correo.com", emailEntity.getEmailFrom());
        assertEquals("Updated body", emailEntity.getEmailBody());
    }

    @Test
    public void testUpdateMail_NotFound() {
        EmailEntity updatedEmail = new EmailEntity();
        updatedEmail.setEmailFrom("corrreo@ecorreo.com");
        updatedEmail.setEmailBody("Updated body");

        when(emailRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class, () -> emailUseCase.updateMail(1L, updatedEmail));
        verify(emailRepository, never()).save(any(EmailEntity.class));
    }

    @Test
    public void testUpdateMail_InvalidStatus() {
        EmailEntity updatedEmail = new EmailEntity();
        updatedEmail.setEmailFrom("correo@correo.com");
        updatedEmail.setEmailBody("Updated body");

        EmailStatus sentStatus = new EmailStatus();
        sentStatus.setId(2L);
        sentStatus.setName("ENVIADO");

        emailEntity.setEmailStatus(sentStatus);

        when(emailRepository.findById(1L)).thenReturn(Optional.of(emailEntity));

        assertThrows(InvalidStatusIdException.class, () -> emailUseCase.updateMail(1L, updatedEmail));
        verify(emailRepository, never()).save(any(EmailEntity.class));
    }
}
