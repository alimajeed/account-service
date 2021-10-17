package com.kyc.accountservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {


    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "user_id")
    Long userId;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Embedded
    Address address;

    @Column(name = "phone")
    String phone;

    @Column(name = "website")
    String website;

    @Embedded
    Company company;

    @Column(name = "device_id", unique = true)
    String deviceId;

    @Column(name = "ssn", unique = true)
    String ssn;

    @Column(name = "dob")
    LocalDate dob;

    @Column(name = "doc_url")
    String docUrl;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "last_updated_at")
    LocalDateTime lastUpdatedAt;
}
