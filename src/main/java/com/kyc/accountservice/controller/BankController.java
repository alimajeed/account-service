package com.kyc.accountservice.controller;

import com.kyc.accountservice.dto.UserDto;
import com.kyc.accountservice.exception.FileStorageException;
import com.kyc.accountservice.exception.UserVerificationFailedException;
import com.kyc.accountservice.payload.ApiResponse;
import com.kyc.accountservice.service.BankService;
import com.kyc.accountservice.validationgroups.CreateRequest;
import com.kyc.accountservice.validationgroups.UpdateRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RequestMapping(BankController.BASE_URL)
@AllArgsConstructor
@Slf4j
@RestController
public class BankController {

    public static final String BASE_URL = "/api/v1/";

    private BankService bankService;

    @PostMapping("create")
    public ResponseEntity<ApiResponse> createUserDetails(@Validated(CreateRequest.class) @ModelAttribute UserDto userDto, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws FileStorageException, UserVerificationFailedException{
        log.info("[createUserDetails], userDto Info : {}", userDto);
        Long userId = bankService.createUserAndGenerateAccount(userDto, file, request);
        ApiResponse apiResponse = ApiResponse.builder()
                .userId(userId)
                .message("user created successfully")
                .build();
        ResponseEntity<ApiResponse> response = ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        log.info("[createUserDetails], response : {}", response);
        return response;
    }

    @PostMapping("update")
    public ResponseEntity<ApiResponse> updateUserDetails(@Validated(UpdateRequest.class) @RequestBody  UserDto userDto) {
        log.info("[updateUserDetails], userDto Info : {}", userDto);
        boolean isUpdated = bankService.updateUserDetails(userDto);
        log.info("isUpdated : {}", isUpdated);
        ApiResponse apiResponse = ApiResponse.builder()
                .userId(userDto.getUserId())
                .message("user updated successfully")
                .build();
        ResponseEntity<ApiResponse> response = ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        log.info("[updateUserDetails], response : {}", response);
        return response;
    }

    @GetMapping("fetch/{userId}")
    public ResponseEntity<UserDto> fetchUserDetails(@PathVariable("userId") Long userId) {
        log.info("[fetchUserDetails], user Id : {}", userId);
        UserDto fetchedUser = bankService.fetchUserDetails(userId);
        ResponseEntity<UserDto> response = ResponseEntity.status(HttpStatus.OK).body(fetchedUser);
        log.info("[updateUserDetails], response : {}", response);
        return response;
    }
}
