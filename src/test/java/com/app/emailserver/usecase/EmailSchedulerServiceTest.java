package com.app.emailserver.usecase;

import com.app.emailserver.domain.model.data.email.EmailEntity;
import com.app.emailserver.domain.model.data.email.EmailStatus;
import com.app.emailserver.domain.usecase.scheduler.EmailSchedulerService;
import com.app.emailserver.infrastructure.repository.EmailRepository;
import com.app.emailserver.infrastructure.repository.EmailStatusRepository;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailSchedulerServiceTest {

    @Mock
    private EmailRepository emailRepository;

    @Mock
    private EmailStatusRepository emailStatusRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private EmailSchedulerService emailSchedulerService;

    private EmailEntity emailEntity;
    private EmailStatus emailStatus;

    @BeforeEach
    public void setUp() {
        emailStatus = new EmailStatus();
        emailStatus.setId(1L);
        emailStatus.setName("SPAM");

        emailEntity = new EmailEntity();
        emailEntity.setEmailId(1L);
        emailEntity.setEmailFrom("test@example.com");
        emailEntity.setEmailStatus(emailStatus);

        ReflectionTestUtils.setField(emailSchedulerService, "spamStatus", "SPAM");
        ReflectionTestUtils.setField(emailSchedulerService, "targetEmail", "correo@correo.com");
    }

    @Test
    public void testMarkEmailsAsSpam() {
        when(emailStatusRepository.findByName("SPAM")).thenReturn(Optional.of(emailStatus));
        when(emailRepository.findByEmailFrom("correo@correo.com")).thenReturn(Collections.singletonList(emailEntity));
        emailSchedulerService.markEmailsAsSpam();

        verify(emailStatusRepository, times(1)).findByName("SPAM");
        verify(emailRepository, times(1)).findByEmailFrom("correo@correo.com");
        verify(emailRepository, times(1)).saveAll(anyList());

    }

    @Test
    public void testMarkEmailsAsSpam_NoSpamStatus() {
        LogCaptor logCaptor = LogCaptor.forClass(EmailSchedulerService.class);
        when(emailStatusRepository.findByName("SPAM")).thenReturn(Optional.empty());
        emailSchedulerService.markEmailsAsSpam();

        verify(emailStatusRepository, times(1)).findByName("SPAM");
        List<String> logs = logCaptor.getWarnLogs();
        assertTrue(logs.contains("No se encontró el estado de spam con el nombre: SPAM"));
        verify(emailRepository, never()).findByEmailFrom(anyString());
        verify(emailRepository, never()).saveAll(anyList());

    }

    @Test
    public void testMarkEmailsAsSpam_NoEmailsFound() {
        LogCaptor logCaptor = LogCaptor.forClass(EmailSchedulerService.class);
        when(emailStatusRepository.findByName("SPAM")).thenReturn(Optional.of(emailStatus));
        when(emailRepository.findByEmailFrom("correo@correo.com")).thenReturn(List.of());

        emailSchedulerService.markEmailsAsSpam();

        verify(emailStatusRepository, times(1)).findByName("SPAM");
        verify(emailRepository, times(1)).findByEmailFrom("correo@correo.com");
        List<String> logs = logCaptor.getWarnLogs();
        assertTrue(logs.contains("No se encontraron correos electrónicos del remitente: correo@correo.com"));
        verify(emailRepository, never()).saveAll(anyList());

    }
}
