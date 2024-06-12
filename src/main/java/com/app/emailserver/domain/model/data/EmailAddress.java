package com.app.emailserver.domain.model.data;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "email_address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmailAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email_id")
    @JsonBackReference
    private EmailEntity emailEntity;
}