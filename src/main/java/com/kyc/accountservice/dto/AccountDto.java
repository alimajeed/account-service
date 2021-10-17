package com.kyc.accountservice.dto;

import com.kyc.accountservice.validationgroups.CreateRequest;
import com.kyc.accountservice.validationgroups.UpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    @Null(groups = {CreateRequest.class, UpdateRequest.class})
    String accountNumber;

    @Null(groups = CreateRequest.class)
    double accountBalance;
}
