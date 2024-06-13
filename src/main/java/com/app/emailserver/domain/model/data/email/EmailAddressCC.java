package com.app.emailserver.domain.model.data.email;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email_address_cc")
@Entity
@Getter
@Setter
public class EmailAddressCC {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_cc_id")
    private Long addressCcId;

    @Column(name = "email")
    private String email;

    @ManyToOne()
    @JoinColumn(name = "email_id")
    @JsonBackReference
    private EmailEntity emailEntity;
}