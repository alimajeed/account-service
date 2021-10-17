package com.kyc.accountservice.dto;

import com.kyc.accountservice.validationgroups.CreateRequest;
import com.kyc.accountservice.validationgroups.UpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @NotNull(groups = CreateRequest.class)
    @NotBlank(groups = {CreateRequest.class, UpdateRequest.class})
    String street;

    @NotNull(groups = CreateRequest.class)
    @NotBlank(groups = {CreateRequest.class, UpdateRequest.class})
    String suite;

    @NotNull(groups = CreateRequest.class)
    @NotBlank(groups = {CreateRequest.class, UpdateRequest.class})
    String city;

    @NotNull(groups = CreateRequest.class)
    @NotBlank(groups = {CreateRequest.class, UpdateRequest.class})
    String zipcode;

    @NotNull
    @Valid
    GeoDto geo;
}
