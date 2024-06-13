package com.app.emailserver.infrastructure.repository;

import com.app.emailserver.domain.model.data.email.EmailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailStatusRepository extends JpaRepository<EmailStatus, Long> {

    Optional<EmailStatus> findByName(String name);
}
