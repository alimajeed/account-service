package com.kyc.accountservice.startup;

import com.kyc.accountservice.domain.Account;
import com.kyc.accountservice.domain.Address;
import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.AddressDto;
import com.kyc.accountservice.repository.AccountRepository;
import com.kyc.accountservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private AccountRepository accountRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadUsers();
    }

    private void loadUsers() {
        User user = User.builder()
                .name("Leanne Graham")
                .email("Nathan@yesenia.net")
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

        User savedUser = userRepository.save(user);
        Account account = Account.builder()
                .accountNumber("JEPA025900000001")
                .accountBalance(0)
                .user(savedUser)
                .build();
        Account savedAccount = accountRepository.save(account);
        System.out.println("user Id : " + savedUser.getUserId());
    }
}
