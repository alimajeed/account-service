package com.kyc.accountservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyc.accountservice.config.AppConfig;
import com.kyc.accountservice.config.FileStorageProperties;
import com.kyc.accountservice.dto.AddressDto;
import com.kyc.accountservice.dto.UserDto;
import com.kyc.accountservice.exception.FileStorageException;
import com.kyc.accountservice.handler.ApiExceptionHandler;
import com.kyc.accountservice.repository.AccountRepository;
import com.kyc.accountservice.repository.UserRepository;
import com.kyc.accountservice.service.BankService;
import com.kyc.accountservice.startup.DevBootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createUserDetails() throws Exception {
        FileInputStream inputFile = new FileInputStream( "docs/alimajeed_resume.pdf");
        MockMultipartFile file = new MockMultipartFile("file", "alimajeed_resume.pdf", "multipart/form-data", inputFile);


        mockMvc.perform(
                MockMvcRequestBuilders.multipart(BankController.BASE_URL + "create")
                        .file(file)
                        .param("name", "Clementine Bauch")
                        .param("email", "Nathan@yesenia.net")
                        .param("phone", "1-770-736-8031 x56442")
                        .param("address.street", "Kulas Light")
                        .param("address.suite", "Kulas Light")
                        .param("address.city", "Kulas Light")
                        .param("address.zipcode", "Kulas Light")
                        .param("deviceId", "1231231")
                        .param("ssn", "123123123")
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", equalTo(2)));
    }

    @Test
    void updateUserDetails() throws Exception {
        UserDto userDto = UserDto.builder()
                .userId(1l)
                .name("Leanne Graham")
                .email("Sincere@april.biz")
                .build();
        mockMvc.perform(post(BankController.BASE_URL + "update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", equalTo(1)));
    }

    @Test
    void fetchUserDetails() throws Exception {
        mockMvc.perform(get(BankController.BASE_URL + "fetch/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId", equalTo(1)));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}