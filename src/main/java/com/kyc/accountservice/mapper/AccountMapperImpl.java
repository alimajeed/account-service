package com.kyc.accountservice.mapper;

import com.kyc.accountservice.domain.Account;
import com.kyc.accountservice.dto.AccountDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AccountMapperImpl implements AccountMapper{
    ModelMapper modelMapper;


    @Override
    public Account accountDtoToAccount(AccountDto accountDto) {
        if (null == accountDto){
            return null;
        }
        return modelMapper.map(accountDto, Account.class);
    }

    @Override
    public AccountDto accountToAccountDto(Account account) {
        if (null == account){
            return null;
        }
        return modelMapper.map(account, AccountDto.class);
    }
}
