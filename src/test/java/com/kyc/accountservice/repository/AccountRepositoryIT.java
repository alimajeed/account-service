package com.kyc.accountservice.repository;

import com.kyc.accountservice.domain.Account;
import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.startup.DevBootstrap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@DirtiesContext
class AccountRepositoryIT {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        DevBootstrap bootstrap = new DevBootstrap(userRepository, accountRepository);
        bootstrap.onApplicationEvent(null);
    }

    @Test
    void findByUser() {
        Optional<User> userOptional = userRepository.findByUserId(1l);
        Optional<Account> accountOptional = accountRepository.findByUser(userOptional.get());
        assertEquals("JEPA025900000001", accountOptional.get().getAccountNumber());
    }
}