package com.app.emailserver.domain.model.data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "emails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmailEntity {

    @Id
    @Column(name = "email_id")
    private Long emailId;

    @Column(name = "email_from")
    private String emailFrom;

    @OneToMany(mappedBy = "emailEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<EmailAddress> emailTo;

    @OneToMany(mappedBy = "emailEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<EmailAddressCC> emailCC ;

    @Column(name = "email_body")
    private String emailBody;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private EmailStatus emailStatus;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

}

