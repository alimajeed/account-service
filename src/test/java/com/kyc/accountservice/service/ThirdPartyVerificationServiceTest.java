package com.kyc.accountservice.service;

import com.kyc.accountservice.dto.AddressDto;
import com.kyc.accountservice.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ThirdPartyVerificationServiceTest {

    RestTemplate restTemplate;

    ThirdPartyVerificationService thirdPartyVerificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restTemplate = new RestTemplate();
        thirdPartyVerificationService = new ThirdPartyVerificationService(restTemplate);
    }

    @Test
    void verifyUser() throws InterruptedException, ExecutionException {

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

        CompletableFuture<Boolean> isVerifiedCompletableFuture = thirdPartyVerificationService.verifyUser(userDto);
        Boolean isVerified = isVerifiedCompletableFuture.get();
        assertTrue(isVerified);
    }

    @Test
    void getFutureResults() {
        CompletableFuture<Boolean> isVerifiedCompletableFuture = CompletableFuture.completedFuture(true);
        boolean isVerified = thirdPartyVerificationService.getFutureResults(isVerifiedCompletableFuture);
        assertTrue(isVerified);
    }
}