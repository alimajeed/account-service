package com.kyc.accountservice.dto;

import com.kyc.accountservice.validationgroups.CreateRequest;
import com.kyc.accountservice.validationgroups.UpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotNull(groups = UpdateRequest.class)
    Long userId;

    @NotNull(groups = CreateRequest.class)
    @NotBlank(groups = CreateRequest.class)
    String name;

    @Email
    @NotNull(groups = CreateRequest.class)
    @NotBlank(groups = CreateRequest.class)
    String email;

    @NotNull(groups = CreateRequest.class)
    @Valid
    AddressDto address;

    @NotNull(groups = CreateRequest.class)
    @NotBlank(groups = CreateRequest.class)
    String phone;

    String website;

    CompanyDto company;

    AccountDto account;

    @NotNull(groups = CreateRequest.class)
    @NotBlank(groups = CreateRequest.class)
    String deviceId;

    @NotNull(groups = CreateRequest.class)
    @NotBlank(groups = CreateRequest.class)
    String ssn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dob;

    @Null
    String docUrl;

    @Null
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime createdAt;

    @Null
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    LocalDateTime lastUpdatedAt;
}
