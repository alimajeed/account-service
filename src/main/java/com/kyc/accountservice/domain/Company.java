package com.kyc.accountservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Company {

    @Column(name = "company_name")
    String name;

    @Column(name = "catch_phrase")
    String catchphrase;

    @Column(name = "bs")
    String bs;
}
