package com.kyc.accountservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyc.accountservice.domain.Address;
import com.kyc.accountservice.domain.User;
import com.kyc.accountservice.dto.AddressDto;
import com.kyc.accountservice.dto.UserDto;
import com.kyc.accountservice.exception.FileStorageException;
import com.kyc.accountservice.exception.UserVerificationFailedException;
import com.kyc.accountservice.handler.ApiExceptionHandler;
import com.kyc.accountservice.payload.ApiResponse;
import com.kyc.accountservice.repository.AccountRepository;
import com.kyc.accountservice.repository.UserRepository;
import com.kyc.accountservice.service.BankService;
import com.kyc.accountservice.startup.DevBootstrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileInputStream;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BankControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BankService bankService;

    @InjectMocks
    private BankController bankController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bankController)
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void createUserDetails() throws Exception, FileStorageException {
        FileInputStream inputFile = new FileInputStream( "docs/alimajeed_resume.pdf");
        MockMultipartFile file = new MockMultipartFile("file", "alimajeed_resume.pdf", "multipart/form-data", inputFile);

        when(bankService.createUserAndGenerateAccount(any(), any(), any())).thenReturn(1l);

        mockMvc.perform(
                MockMvcRequestBuilders.multipart(BankController.BASE_URL + "create")
                        .file(file)
                        .param("name", "Leanne Graham")
                        .param("email", "Sincere@april.biz")
                        .param("phone", "1-770-736-8031 x56442")
                        .param("address.street", "Kulas Light")
                        .param("address.suite", "Kulas Light")
                        .param("address.city", "Kulas Light")
                        .param("address.zipcode", "Kulas Light")
                        .param("deviceId", "1231231")
                        .param("ssn", "123123123")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", equalTo(1)));
    }

    @Test
    void createUserDetailsFailedWithVerification() throws Exception, FileStorageException {
        FileInputStream inputFile = new FileInputStream( "docs/alimajeed_resume.pdf");
        MockMultipartFile file = new MockMultipartFile("file", "alimajeed_resume.pdf", "multipart/form-data", inputFile);

        when(bankService.createUserAndGenerateAccount(any(), any(), any())).thenThrow(new UserVerificationFailedException("aho verification failed for given user"));

        mockMvc.perform(
                MockMvcRequestBuilders.multipart(BankController.BASE_URL + "create")
                        .file(file)
                        .param("name", "Leanne Graham")
                        .param("email", "Sincere@april.biz")
                        .param("phone", "1-770-736-8031 x56442")
                        .param("address.street", "Kulas Light")
                        .param("address.suite", "Kulas Light")
                        .param("address.city", "Kulas Light")
                        .param("address.zipcode", "Kulas Light")
                        .param("deviceId", "1231231")
                        .param("ssn", "123123123")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isPreconditionFailed());
    }

    @Test
    void createUserDetailsFailedWithValidation() throws Exception {
        FileInputStream inputFile = new FileInputStream( "docs/alimajeed_resume.pdf");
        MockMultipartFile file = new MockMultipartFile("file", "alimajeed_resume.pdf", "multipart/form-data", inputFile);

        mockMvc.perform(
                MockMvcRequestBuilders.multipart(BankController.BASE_URL + "create")
                        .file(file)
                        .param("email", "Sincere@april.biz")
                        .param("phone", "1-770-736-8031 x56442")
                        .param("address.street", "Kulas Light")
                        .param("address.suite", "Kulas Light")
                        .param("address.city", "Kulas Light")
                        .param("address.zipcode", "Kulas Light")
                        .param("deviceId", "1231231")
                        .param("ssn", "123123123")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUserDetails() throws Exception {
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

        when(bankService.updateUserDetails(userDto)).thenReturn(true);

        mockMvc.perform(post(BankController.BASE_URL + "update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", equalTo(2)));
    }

    @Test
    void fetchUserDetails() throws Exception {
        UserDto user = UserDto.builder()
                .userId(2l)
                .name("Leanne Graham")
                .email("Nathan@yesenia.net")
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

        when(bankService.fetchUserDetails(anyLong())).thenReturn(user);

        mockMvc.perform(get(BankController.BASE_URL + "fetch/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", equalTo(2)));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}