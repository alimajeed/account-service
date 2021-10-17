package com.kyc.accountservice.service;

import com.kyc.accountservice.domain.Account;
import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.AccountDto;
import com.kyc.accountservice.mapper.AccountMapper;
import com.kyc.accountservice.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{

    AccountMapper accountMapper;
    AccountRepository accountRepository;

    @Override
    public Account createAccount(User createdUser) {
        Account account = Account.builder()
                .accountNumber(createAccountNumber(createdUser))
                .accountBalance(0)
                .user(createdUser)
                .build();
        Account createdAccount = accountRepository.save(account);
        return createdAccount;
    }

    @Override
    public AccountDto mapToAccountDto(Account createdAccount) {
        return accountMapper.accountToAccountDto(createdAccount);
    }

    @Override
    public Optional<Account> findByUser(User user) {
        return accountRepository.findByUser(user);
    }

    private String timeString (){
        LocalDateTime now = LocalDateTime.now();
        String hour = String.format("%01d" , now.getHour());
        String minute = String.format("%01d" , now.getMinute());
        String seconds = String.format("%01d" , now.getSecond());
        return hour+minute+seconds;
    }

    private String createAccountNumber(User createdUser) {
        return "JETA" + timeString() + String.format("%05d" , createdUser.getUserId());
    }
}
