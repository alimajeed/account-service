package com.kyc.accountservice.service;

import com.kyc.accountservice.domain.Account;
import com.kyc.accountservice.domain.Address;
import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.AccountDto;
import com.kyc.accountservice.mapper.AccountMapper;
import com.kyc.accountservice.mapper.AccountMapperImpl;
import com.kyc.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    AccountMapper accountMapper;
    AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountMapper = new AccountMapperImpl(new ModelMapper());
        accountService = new AccountServiceImpl(accountMapper, accountRepository);
    }

    @Test
    void createAccount() {
        User user = User.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(Address.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        String accountNumber = "JEPA124533000001";
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .accountBalance(0)
                .user(user)
                .build();

        when(accountRepository.save(any())).thenReturn(account);

        Account createdAccount = accountService.createAccount(user);
        assertEquals(accountNumber, createdAccount.getAccountNumber());
    }

    @Test
    void mapToAccountDto() {
        User user = User.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(Address.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        String accountNumber = "JEPA124533000001";
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .accountBalance(0)
                .user(user)
                .build();

        AccountDto mappedAccountDto = accountService.mapToAccountDto(account);
        assertEquals(account.getAccountNumber(), mappedAccountDto.getAccountNumber());
    }

    @Test
    void findByUser() {
        User user = User.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(Address.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        String accountNumber = "JEPA124533000001";
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .accountBalance(0)
                .user(user)
                .build();

        when(accountRepository.findByUser(any())).thenReturn(Optional.ofNullable(account));

        Optional<Account> accountOptional = accountService.findByUser(user);
        assertEquals(accountNumber, accountOptional.get().getAccountNumber());
    }
}