package com.app.emailserver.repository;

import com.app.emailserver.domain.model.data.EmailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailStatusRepository extends JpaRepository<EmailStatus, Long> {

}
