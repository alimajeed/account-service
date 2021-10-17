package com.kyc.accountservice.service;

import com.kyc.accountservice.domain.Account;
import com.kyc.accountservice.domain.Address;
import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.AddressDto;
import com.kyc.accountservice.dto.UserDto;
import com.kyc.accountservice.exception.EntityNotFoundException;
import com.kyc.accountservice.exception.FileStorageException;
import com.kyc.accountservice.exception.UserVerificationFailedException;
import com.kyc.accountservice.mapper.AccountMapper;
import com.kyc.accountservice.mapper.AccountMapperImpl;
import com.kyc.accountservice.mapper.UserMapper;
import com.kyc.accountservice.mapper.UserMapperImpl;
import com.kyc.accountservice.repository.AccountRepository;
import com.kyc.accountservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankServiceImplTest {

    @Mock
    FileStorageService fileStorageService;

    @Mock
    ThirdPartyVerificationService verificationService;

    @Mock
    HttpServletRequest httpServletRequest;

    AccountMapper accountMapper;
    @Mock
    AccountRepository accountRepository;
    AccountService accountService;

    UserMapper userMapper;

    @Mock
    UserRepository userRepository;
    UserService userService;

    BankService bankService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ModelMapper modelMapper = new ModelMapper();
        userMapper = new UserMapperImpl(modelMapper);
        userService = new UserServiceImpl(userMapper, userRepository);

        accountMapper = new AccountMapperImpl(modelMapper);
        accountService = new AccountServiceImpl(accountMapper, accountRepository);
        bankService = new BankServiceImpl(fileStorageService, verificationService, accountService, userService);
    }

    @Test
    void createUserAndGenerateAccount() throws FileStorageException, IOException {

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

        Account account = Account.builder()
                .accountNumber("JETA235412000001")
                .accountBalance(0)
                .user(user)
                .build();

        FileInputStream inputFile = new FileInputStream( "docs/alimajeed_resume.pdf");
        MockMultipartFile file = new MockMultipartFile("file", "alimajeed_resume.pdf", "multipart/form-data", inputFile);

        when(verificationService.verifyUser(any())).thenReturn(CompletableFuture.completedFuture(true));
        when(fileStorageService.storeFile(any())).thenReturn(CompletableFuture.completedFuture("filepath"));
        when(userRepository.save(any())).thenReturn(user);
        when(userService.saveUser(any())).thenReturn(user);
        when(accountRepository.save(any())).thenReturn(account);
        when(accountService.createAccount(user)).thenReturn(account);
        when(verificationService.getFutureResults(any())).thenReturn(true);


        Long userId = bankService.createUserAndGenerateAccount(userMapper.userToUserDto(user), file, httpServletRequest);
        assertEquals(2, userId);
    }

    @Test
    void createUserAndGenerateAccountFailedWithVerification() throws FileStorageException, IOException {

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

        Account account = Account.builder()
                .accountNumber("JETA235412000001")
                .accountBalance(0)
                .user(user)
                .build();

        FileInputStream inputFile = new FileInputStream( "docs/alimajeed_resume.pdf");
        MockMultipartFile file = new MockMultipartFile("file", "alimajeed_resume.pdf", "multipart/form-data", inputFile);

        when(verificationService.verifyUser(any())).thenReturn(CompletableFuture.completedFuture(false));
        when(fileStorageService.storeFile(any())).thenReturn(CompletableFuture.completedFuture("filepath"));
        when(userRepository.save(any())).thenReturn(user);
        when(userService.saveUser(any())).thenReturn(user);
        when(accountRepository.save(any())).thenReturn(account);
        when(accountService.createAccount(user)).thenReturn(account);

        Assertions.assertThrows(UserVerificationFailedException.class, () -> bankService.createUserAndGenerateAccount(userMapper.userToUserDto(user), file, httpServletRequest));
    }

    @Test
    void updateUserDetails() {
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

        UserDto userDto = UserDto.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(AddressDto.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        when(userService.findByUserId(anyLong())).thenReturn(Optional.of(user));
        when(verificationService.verifyUser(any())).thenReturn(CompletableFuture.completedFuture(true));
        when(userRepository.save(any())).thenReturn(user);
        when(userService.saveUser(any())).thenReturn(user);
        when(verificationService.getFutureResults(any())).thenReturn(true);

        boolean isUpdated = bankService.updateUserDetails(userDto);
        assertTrue(isUpdated);
    }

    @Test
    void updateUserDetailsWithVerificationFailed() {
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

        UserDto userDto = UserDto.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(AddressDto.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        when(userService.findByUserId(anyLong())).thenReturn(Optional.of(user));
        when(verificationService.verifyUser(any())).thenReturn(CompletableFuture.completedFuture(false));
        when(userRepository.save(any())).thenReturn(user);
        when(userService.saveUser(any())).thenReturn(user);
        when(verificationService.getFutureResults(any())).thenReturn(false);

        Assertions.assertThrows(UserVerificationFailedException.class, () -> bankService.updateUserDetails(userDto));
    }

    @Test
    void updateUserDetailsWithEntityNotFound() {
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

        UserDto userDto = UserDto.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(AddressDto.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        when(userService.findByUserId(anyLong())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(EntityNotFoundException.class, () -> bankService.updateUserDetails(userDto));
    }

    @Test
    void fetchUserDetails() {
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

        UserDto userDto = UserDto.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .address(AddressDto.builder()
                        .street("Douglas Extension")
                        .suite("Suite 847")
                        .city("McKenziehaven")
                        .zipcode("59590-4157")
                        .build())
                .phone("1-463-123-4447")
                .deviceId("123123123")
                .ssn("123456789")
                .build();

        Account account = Account.builder()
                .accountNumber("JEPA124533000001")
                .accountBalance(0)
                .user(user)
                .build();

        when(userService.findByUserId(anyLong())).thenReturn(Optional.of(user));
        when(accountService.findByUser(any())).thenReturn(Optional.of(account));

        UserDto fetchUserDetails = bankService.fetchUserDetails(2l);
        assertEquals(2, fetchUserDetails.getUserId());
    }
}