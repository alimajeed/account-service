package com.kyc.accountservice.mapper;

import com.kyc.accountservice.domain.Account;
import com.kyc.accountservice.dto.AccountDto;

public interface AccountMapper {
    Account accountDtoToAccount (AccountDto accountDto);
    AccountDto accountToAccountDto (Account account);
}
