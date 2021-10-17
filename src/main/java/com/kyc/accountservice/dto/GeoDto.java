package com.kyc.accountservice.dto;

import com.kyc.accountservice.validationgroups.CreateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoDto {
    @NotNull(groups = CreateRequest.class)
    String lat;

    @NotNull(groups = CreateRequest.class)
    String lng;
}
