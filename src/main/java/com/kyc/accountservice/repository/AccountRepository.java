package com.kyc.accountservice.repository;

import com.kyc.accountservice.domain.Account;
import com.kyc.accountservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUser(User user);
}
