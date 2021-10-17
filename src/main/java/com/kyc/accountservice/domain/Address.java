package com.kyc.accountservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    @Column(name = "street")
    String street;

    @Column(name = "suite")
    String suite;

    @Column(name = "city")
    String city;

    @Column(name = "zipcode")
    String zipcode;

    @Embedded
    Geo geo;
}
