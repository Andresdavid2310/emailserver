package com.app.emailserver.infrastructure.repository;

import com.app.emailserver.domain.model.data.email.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Long> {

    List<EmailEntity> findByEmailFrom(String targetEmail);
}
