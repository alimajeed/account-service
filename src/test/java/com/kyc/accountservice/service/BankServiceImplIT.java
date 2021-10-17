package com.kyc.accountservice.service;

import com.kyc.accountservice.dto.AddressDto;
import com.kyc.accountservice.dto.UserDto;
import com.kyc.accountservice.exception.FileStorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class BankServiceImplIT {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    ThirdPartyVerificationService verificationService;

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Mock
    HttpServletRequest httpServletRequest;

    @Autowired
    BankService bankService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createUserAndGenerateAccount() throws IOException, FileStorageException {
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
                .deviceId("1231231233")
                .ssn("1234567892")
                .build();

        FileInputStream inputFile = new FileInputStream( "docs/alimajeed_resume.pdf");
        MockMultipartFile file = new MockMultipartFile("file", "alimajeed_resume.pdf", "multipart/form-data", inputFile);

        Long userId = bankService.createUserAndGenerateAccount(userDto, file, httpServletRequest);
    }

    @Test
    void updateUserDetails() {
        UserDto userDto = UserDto.builder()
                .userId(1l)
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

        boolean isUpdated = bankService.updateUserDetails(userDto);
        assertTrue(isUpdated);
    }

    @Test
    void fetchUserDetails() {
        UserDto userDto = bankService.fetchUserDetails(1l);
        assertEquals(1l, userDto.getUserId());
        assertEquals("JEPA025900000001", userDto.getAccount().getAccountNumber());
    }
}