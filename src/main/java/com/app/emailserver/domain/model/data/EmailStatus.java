package com.app.emailserver.domain.model.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Table(name = "email_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmailStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "emailStatus")
    @JsonBackReference
    private Set<EmailEntity> emails;

}