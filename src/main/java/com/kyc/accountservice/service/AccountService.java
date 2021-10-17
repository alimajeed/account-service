package com.kyc.accountservice.service;

import com.kyc.accountservice.domain.Account;
import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.AccountDto;

import java.util.Optional;

public interface AccountService {

    Account createAccount(User createdUser);
    AccountDto mapToAccountDto(Account createdAccount);
    Optional<Account> findByUser(User user);
}
